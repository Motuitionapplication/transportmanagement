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

public class CustomerRegisterActivity extends AppCompatActivity {

    // Declare UI elements
    private TextInputLayout firstNameLayout, lastNameLayout, ageLayout, addressLayout, emailLayout, usernameLayout, phoneLayout, passwordLayout;
    private TextInputEditText firstNameEditText, lastNameEditText, ageEditText, addressEditText, emailEditText, usernameEditText, phoneEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole; // Store selected role

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        // Retrieve selected role
        selectedRole = getIntent().getStringExtra("ROLE");

        // Ensure role is "customer"
        if (selectedRole == null || !selectedRole.equalsIgnoreCase("customer")) {
            Toast.makeText(this, "Error: Invalid Role!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize UI components
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        ageLayout = findViewById(R.id.ageLayout);
        addressLayout = findViewById(R.id.addressLayout);
        emailLayout = findViewById(R.id.emailLayout);
        usernameLayout = findViewById(R.id.usernameLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.username);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);

        createAccountButton = findViewById(R.id.createAccountButton);

        // Set validation on focus change
        setValidationOnFocus(firstNameEditText, firstNameLayout, "Please enter your first name");
        setValidationOnFocus(lastNameEditText, lastNameLayout, "Please enter your last name");
        setValidationOnFocus(emailEditText, emailLayout, "Please enter a valid email");
        setValidationOnFocus(usernameEditText, usernameLayout, "Please enter your username");
        setValidationOnFocus(addressEditText, addressLayout, "Please enter your address");
        setValidationOnFocus(passwordEditText, passwordLayout, "Password must be at least 6 characters");

        // Age validation (only numbers)
        ageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String age = ageEditText.getText().toString().trim();
                if (age.isEmpty() || !age.matches("\\d+") || Integer.parseInt(age) < 1 || Integer.parseInt(age) > 120) {
                    ageLayout.setError("Enter a valid age (1-120)");
                    ageEditText.setBackgroundResource(R.drawable.edit_text_error);
                } else {
                    ageLayout.setError(null);
                    ageEditText.setBackgroundResource(R.drawable.edit_text_background);
                }
            }
        });

        // Phone number validation (must be 10 digits)
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

        // Register button click
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String age = getSafeText(ageEditText);
            String address = getSafeText(addressEditText);
            String email = getSafeText(emailEditText);
            String username = getSafeText(usernameEditText);
            String phone = getSafeText(phoneEditText);
            String password = getSafeText(passwordEditText);

            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || address.isEmpty() || email.isEmpty() ||
                    username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register API call
            registerUser(firstName, lastName, age, address, email, username, phone, password);
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

    // API Call to register user
    private void registerUser(String firstName, String lastName, String age, String address, String email, String username,
                              String phone, String password) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                //.baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        CustomerRegisterRequest registerRequest = new CustomerRegisterRequest(firstName, lastName, age, address, email, username, phone, password, "customer");

        Call<String> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CustomerRegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomerRegisterActivity.this, CustomerHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CustomerRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CustomerRegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
