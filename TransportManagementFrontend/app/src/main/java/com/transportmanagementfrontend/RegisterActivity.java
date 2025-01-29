package com.transportmanagementfrontend;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set onClickListener for the Create Account button
        createAccountButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() ) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields and select a role", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}



