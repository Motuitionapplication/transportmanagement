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

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, ageEditText, addressEditText, usernameEditText, phoneEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole; // Store the role selected on the previous screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        // Retrieve the selected role from the intent.
        selectedRole = getIntent().getStringExtra("ROLE");

        // Ensure role is "customer", otherwise exit
        if (selectedRole == null || !selectedRole.equalsIgnoreCase("customer")) {
            Toast.makeText(this, "Error: Invalid Role!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        usernameEditText = findViewById(R.id.username);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set click listener for the Create Account button
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String age = getSafeText(ageEditText);
            String address = getSafeText(addressEditText);
            String username = getSafeText(usernameEditText);
            String phone = getSafeText(phoneEditText);
            String password = getSafeText(passwordEditText);

            // Validate that no field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || address.isEmpty() ||
                    username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(CustomerRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the API for registration
            registerUser(firstName, lastName, age, address, username, phone, password);
        });
    }

    private String getSafeText(EditText editText) {
        return (editText != null && editText.getText() != null) ? editText.getText().toString().trim() : "";
    }

    private void registerUser(String firstName, String lastName, String age, String address, String username,
                              String phone, String password) {
        // Enable lenient JSON parsing
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        CustomerRegisterRequest registerRequest = new CustomerRegisterRequest(firstName, lastName, age, address, username, phone, password, "customer");

        // Make the API call
        Call<String> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body();
                    Toast.makeText(CustomerRegisterActivity.this, "Welcome to Customer Page! " + message, Toast.LENGTH_SHORT).show();

                    // Redirect to Customer Home Page
                    Intent intent = new Intent(CustomerRegisterActivity.this, CustomerHomeActivity.class);
                    intent.putExtra("ROLE", "customer");
                    intent.putExtra("FIRST_NAME", firstName);
                    startActivity(intent);
                    finish();
                } else {
                    // Log the raw error response for debugging
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API_ERROR", "Raw error response: " + errorResponse);
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Error reading error response", e);
                    }
                    Toast.makeText(CustomerRegisterActivity.this, "Registration Failed. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CustomerRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_FAILURE", "Request failed", t);
            }
        });
    }
}
