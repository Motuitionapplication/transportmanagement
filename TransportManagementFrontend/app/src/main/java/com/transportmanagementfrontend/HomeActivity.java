package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Make sure this layout is created

        // Initialize the TextViews
        welcomeTextView = findViewById(R.id.welcomeTextView);
        usernameTextView = findViewById(R.id.usernameTextView);

        // Get the email and selected role from the Intent
        String username = getIntent().getStringExtra("USERNAME");
        String selectedRole = getIntent().getStringExtra("ROLE");

        // Set the welcome message
        if (selectedRole != null && username != null) {
            welcomeTextView.setText("Welcome, " + selectedRole);
            usernameTextView.setText("Your username: " + username);
        } else {
            welcomeTextView.setText("Welcome!");
        }
    }
}
