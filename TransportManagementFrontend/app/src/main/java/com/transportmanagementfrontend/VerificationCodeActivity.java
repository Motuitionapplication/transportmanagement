package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText verificationCodeInput;
    private Button submitButton;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        // Initialize views
        verificationCodeInput = findViewById(R.id.verificationCodeInput);
        submitButton = findViewById(R.id.submitButton);
        emailTextView = findViewById(R.id.emailTextView);  // Correct reference here

        // Get email from the Intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        emailTextView.setText("Code sent to: " + email);  // Display email

        // Set click listener for the Submit button
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    // Method to handle "Submit" button logic
    private void handleSubmit() {
        String code = verificationCodeInput.getText().toString().trim();

        // Input validation for empty verification code field
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(VerificationCodeActivity.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Placeholder for actual verification logic (e.g., backend API check)
        Toast.makeText(this, "Verification code entered!", Toast.LENGTH_SHORT).show();

        // Proceed to Change Password activity
        Intent intent = new Intent(VerificationCodeActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
