package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DriverHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        // Display a welcome message
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome To Driver Page");
    }
}
