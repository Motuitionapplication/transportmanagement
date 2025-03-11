package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerRegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, addressEditText, vehicleTypeEditText, usernameEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole = "Owner"; // Fixed role for owner registration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        vehicleTypeEditText = findViewById(R.id.vehicleType);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set click listener for the Create Account button
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String phone = getSafeText(phoneEditText);
            String address = getSafeText(addressEditText);
            String vehicleType = getSafeText(vehicleTypeEditText);
            String username = getSafeText(usernameEditText);
            String password = getSafeText(passwordEditText);

            // Validate fields
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                    vehicleType.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(OwnerRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the API for registration
            registerOwner(firstName, lastName, phone, address, vehicleType, username, password, selectedRole);
        });
    }

    private String getSafeText(EditText editText) {
        return (editText != null && editText.getText() != null) ? editText.getText().toString().trim() : "";
    }

    private void registerOwner(String firstName, String lastName, String phone, String address, String vehicleType, String username,
                               String password, String role) {
        // Enable lenient JSON parsing
        Gson gson = new GsonBuilder()
                .setLenient() // Allow minor JSON formatting issues
                .create();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/") // Replace with your actual API URL
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create()) // Scalars converter first
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        OwnerRegisterRequest ownerRegisterRequest = new OwnerRegisterRequest(firstName, lastName, phone, address, vehicleType, username, password, role);

        // Make the API call
        Call<String> call = apiService.registerOwner(ownerRegisterRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body();
                    Toast.makeText(OwnerRegisterActivity.this, "Welcome to Owner Page! " + message, Toast.LENGTH_SHORT).show();

                    // Redirect user to Owner Home Screen
                    Intent intent = new Intent(OwnerRegisterActivity.this, OwnerHomeActivity.class);
                    intent.putExtra("ROLE", selectedRole);
                    intent.putExtra("FIRST_NAME", firstName);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API_ERROR", "Raw error response: " + errorResponse);
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Error reading error response", e);
                    }
                    Toast.makeText(OwnerRegisterActivity.this, "Registration Failed. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OwnerRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_FAILURE", "Request failed", t);
            }
        });
    }
}
