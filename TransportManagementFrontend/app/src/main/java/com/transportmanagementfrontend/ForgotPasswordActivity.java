package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText emailEditText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);  // Use the correct layout file

        // Initialize views
        emailEditText = findViewById(R.id.email);
        nextButton = findViewById(R.id.nextButton);

        // Set click listener for the Next button
        nextButton.setOnClickListener(v -> handleNext());
    }

    // Method to handle "Next" button logic (Validate email and navigate to verification)
    private void handleNext() {
        String email = emailEditText.getText().toString().trim();

        // Input validation for empty email field
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email validation passed, move to Verification Code Activity
        Intent intent = new Intent(ForgotPasswordActivity.this, VerificationCodeActivity.class);
        intent.putExtra("email", email);  // Passing email to the next activity
        startActivity(intent);
    }
}
