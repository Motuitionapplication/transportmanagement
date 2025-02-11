package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Make sure this layout is created

        // Initialize the TextViews
        welcomeTextView = findViewById(R.id.welcomeTextView);
        emailTextView = findViewById(R.id.emailTextView);

        // Get the email and selected role from the Intent
        String email = getIntent().getStringExtra("EMAIL");
        String selectedRole = getIntent().getStringExtra("ROLE");

        // Set the welcome message
        if (selectedRole != null && email != null) {
            welcomeTextView.setText("Welcome, " + selectedRole);
            emailTextView.setText("Your email: " + email);
        } else {
            welcomeTextView.setText("Welcome!");
        }
    }
}
