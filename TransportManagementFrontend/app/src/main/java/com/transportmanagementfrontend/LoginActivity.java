package com.transportmanagementfrontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordText;
    private TextView newUserText;
    private Button registerButton;
    private ImageView logoImageView;
    private TextView roleTextView; // Added to display role

    private String selectedRole;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Ensure the layout file is correct

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordText = findViewById(R.id.forgotPassword);
        newUserText = findViewById(R.id.newUserText);
        registerButton = findViewById(R.id.registerButton);
        logoImageView = findViewById(R.id.logoImageView);
        roleTextView = findViewById(R.id.roleTextView); // Initialize roleTextView

        // Get the selected role from the intent
        selectedRole = getIntent().getStringExtra("ROLE");

        // Set the role-specific message or change UI as necessary
        if (selectedRole != null) {
            // Display the selected role in the roleTextView
            roleTextView.setText("Logged In As: " + selectedRole);
            // Optionally, show a toast
            Toast.makeText(this, "Logged In As: " + selectedRole, Toast.LENGTH_SHORT).show();
        } else {
            roleTextView.setText("Logged In As: Unknown");
        }

        // Set the logo visibility (optional check)
        logoImageView.setVisibility(ImageView.VISIBLE); // Ensure the logo is visible

        // Set click listeners for buttons
        loginButton.setOnClickListener(v -> handleLogin());
        forgotPasswordText.setOnClickListener(v -> handleForgotPassword());
        newUserText.setOnClickListener(v -> handleNewUser());
        registerButton.setOnClickListener(v -> handleRegister());
    }

    // Method to handle login logic
    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Input validation for empty fields
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Placeholder for actual login logic (e.g., backend API authentication)
        if (email.equals("test@example.com") && password.equals("password")) {
            Toast.makeText(this, "Login successful as: " + selectedRole, Toast.LENGTH_SHORT).show();
            // Navigate to MainActivity or dashboard based on the role
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity so the user cannot go back to it
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle forgot password logic (Redirect to ForgotPasswordActivity)
    private void handleForgotPassword() {
        // Redirect to ForgotPasswordActivity
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    // Method to handle new user (Navigating to RegisterActivity)
    private void handleNewUser() {
        // Navigate to RegisterActivity for new user registration
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    // Method to handle register button logic (Navigating to RegisterActivity)
    private void handleRegister() {
        // Navigate to RegisterActivity for user registration
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
