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

public class DriverRegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, dlNumberEditText, vehicleNumberEditText, usernameEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole; // Store the role selected on the previous screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register); // Set to the correct layout

        // Retrieve the selected role from the intent.
        selectedRole = getIntent().getStringExtra("ROLE");

        // Ensure role is passed
        if (selectedRole == null || selectedRole.trim().isEmpty()) {
            Toast.makeText(DriverRegisterActivity.this, "Error: Role not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneEditText = findViewById(R.id.phone);
        dlNumberEditText = findViewById(R.id.dlNumber); // Driver's License Number
        vehicleNumberEditText = findViewById(R.id.vehicleNumber); // Vehicle Number
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set click listener for the Create Account button
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String phone = getSafeText(phoneEditText);
            String dlNumber = getSafeText(dlNumberEditText);
            String vehicleNumber = getSafeText(vehicleNumberEditText);
            String username = getSafeText(usernameEditText);
            String password = getSafeText(passwordEditText);

            // Validate that no field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || dlNumber.isEmpty() ||
                    vehicleNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(DriverRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the API for registration
            registerDriver(firstName, lastName, phone, dlNumber, vehicleNumber, username, password, selectedRole);
        });
    }

    private String getSafeText(EditText editText) {
        return (editText != null && editText.getText() != null) ? editText.getText().toString().trim() : "";
    }

    private void registerDriver(String firstName, String lastName, String phone, String dlNumber, String vehicleNumber,
                                String username, String password, String role) {
        // Enable lenient JSON parsing
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Create Retrofit instance using ScalarsConverterFactory and GsonConverterFactory.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        DriverRegisterRequest registerRequest = new DriverRegisterRequest(firstName, lastName, phone, dlNumber, vehicleNumber, username, password, role);

        // Make the API call, expecting a String response
        Call<String> call = apiService.registerDriver(registerRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Retrieve the plain string message from the response
                    String message = response.body();
                    Toast.makeText(DriverRegisterActivity.this, "Welcome to " + selectedRole + " Page! " + message, Toast.LENGTH_SHORT).show();

                    // Redirect user to the driver home screen
                    Intent intent = new Intent(DriverRegisterActivity.this, DriverHomeActivity.class);
                    intent.putExtra("ROLE", selectedRole);
                    intent.putExtra("FIRST_NAME", firstName); // Pass first name
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
                    Toast.makeText(DriverRegisterActivity.this, "Registration Failed. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DriverRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_FAILURE", "Request failed", t);
            }
        });
    }
}
