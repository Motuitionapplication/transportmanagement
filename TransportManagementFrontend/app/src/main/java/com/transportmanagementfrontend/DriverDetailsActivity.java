package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DriverDetailsActivity extends AppCompatActivity {

    private TextView txtDriverName, txtPhone, txtEmail, txtDlNumber, txtVehicleNumber, txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        // Initialize UI components
        txtDriverName = findViewById(R.id.txtDriverName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtDlNumber = findViewById(R.id.txtDlNumber);
        txtVehicleNumber = findViewById(R.id.txtVehicleNumber);
        txtUsername = findViewById(R.id.txtUsername);

        // Get driver details from Intent
        DriverParameter driver = (DriverParameter) getIntent().getSerializableExtra("DRIVER");

        if (driver != null) {
            // Set values to text fields
            txtDriverName.setText(driver.getFirstName() + " " + driver.getLastName());
            txtPhone.setText("Phone: " + driver.getPhone());
            txtEmail.setText("Email: " + driver.getEmail());
            txtDlNumber.setText("DL Number: " + driver.getDlNumber());
            txtVehicleNumber.setText("Vehicle No: " + driver.getVehicleNumber());
            txtUsername.setText("Username: " + driver.getUsername());
        }
    }
}
