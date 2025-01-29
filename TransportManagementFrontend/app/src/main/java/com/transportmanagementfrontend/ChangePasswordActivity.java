package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText newPasswordInput;
    private EditText reenterPasswordInput;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);  // Set the correct layout file

        // Initialize views
        newPasswordInput = findViewById(R.id.newPasswordInput);
        reenterPasswordInput = findViewById(R.id.reenterPasswordInput);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        // Set click listener for the Change Password button
        changePasswordButton.setOnClickListener(v -> handleChangePassword());
    }

    // Method to handle "Change Password" button logic
    private void handleChangePassword() {
        String newPassword = newPasswordInput.getText().toString().trim();
        String reenteredPassword = reenterPasswordInput.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(reenteredPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(reenteredPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Placeholder for actual password change logic (e.g., backend API call)
        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();

        // Redirect to Login page
        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the activity to prevent going back
    }
}
