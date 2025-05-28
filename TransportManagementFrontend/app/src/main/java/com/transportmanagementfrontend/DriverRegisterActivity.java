package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverRegisterActivity extends AppCompatActivity {

    // UI Elements
    private TextInputLayout firstNameLayout, lastNameLayout, phoneLayout, emailLayout, dlNumberLayout, vehicleNumberLayout, usernameLayout, passwordLayout;
    private TextInputEditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText, dlNumberEditText, vehicleNumberEditText, usernameEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole = "Driver"; // Default role

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        // Initialize UI Components
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        emailLayout = findViewById(R.id.emailLayout);
        dlNumberLayout = findViewById(R.id.dlNumberLayout);
        vehicleNumberLayout = findViewById(R.id.vehicleNumberLayout);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        dlNumberEditText = findViewById(R.id.dlNumber);
        vehicleNumberEditText = findViewById(R.id.vehicleNumber);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        createAccountButton = findViewById(R.id.createAccountButton);

        // Set field validations
        setValidationOnFocus(firstNameEditText, firstNameLayout, "Please enter your first name");
        setValidationOnFocus(lastNameEditText, lastNameLayout, "Please enter your last name");
        setValidationOnFocus(emailEditText, emailLayout, "Please enter a valid email");
        setValidationOnFocus(usernameEditText, usernameLayout, "Please enter your username");
        setValidationOnFocus(passwordEditText, passwordLayout, "Password must be at least 6 characters");
        setValidationOnFocus(dlNumberEditText, dlNumberLayout, "Please enter a valid Driver's License Number");
        setValidationOnFocus(vehicleNumberEditText, vehicleNumberLayout, "Please enter your Vehicle Number");

        // Phone number validation (10-digit requirement)
        phoneEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phone = phoneEditText.getText().toString().trim();
                if (!phone.matches("\\d{10}")) {
                    phoneLayout.setError("Enter a valid 10-digit phone number");
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_error);
                } else {
                    phoneLayout.setError(null);
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_background);
                }
            }
        });

        // Register button click listener
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String phone = getSafeText(phoneEditText);
            String email = getSafeText(emailEditText);
            String dlNumber = getSafeText(dlNumberEditText);
            String vehicleNumber = getSafeText(vehicleNumberEditText);
            String username = getSafeText(usernameEditText);
            String password = getSafeText(passwordEditText);

            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                    dlNumber.isEmpty() || vehicleNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register API call
            registerDriver(firstName, lastName, phone, email, dlNumber, vehicleNumber, username, password);
        });
    }

    // Function to set validation on focus change
    private void setValidationOnFocus(TextInputEditText editText, TextInputLayout layout, String errorMessage) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (editText.getText().toString().trim().isEmpty()) {
                    layout.setError(errorMessage);
                    editText.setBackgroundResource(R.drawable.edit_text_error);
                } else {
                    layout.setError(null);
                    editText.setBackgroundResource(R.drawable.edit_text_background);
                }
            }
        });
    }

    // Function to safely get text from input
    private String getSafeText(TextInputEditText editText) {
        return (editText != null && editText.getText() != null) ? editText.getText().toString().trim() : "";
    }

    // API Call to register driver
    private void registerDriver(String firstName, String lastName, String phone, String email,
                                String dlNumber, String vehicleNumber, String username, String password) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                //.baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        DriverRegisterRequest registerRequest = new DriverRegisterRequest(firstName, lastName, phone, email, dlNumber, vehicleNumber, username, password, "driver");

        Call<String> call = apiService.registerDriver(registerRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(DriverRegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DriverRegisterActivity.this, DriverHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DriverRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DriverRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
