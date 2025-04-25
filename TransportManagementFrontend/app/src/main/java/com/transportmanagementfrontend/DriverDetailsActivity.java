package com.transportmanagementfrontend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DriverDetailsActivity extends AppCompatActivity {

    private TextView txtDriverName, txtPhone, txtEmail, txtAddress, txtDlNumber, txtVehicleNumber, txtStatus;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        // Initialize UI components
        txtDriverName    = findViewById(R.id.txtDriverName);
        txtPhone         = findViewById(R.id.txtPhone);
        txtEmail         = findViewById(R.id.txtEmail);
        txtAddress       = findViewById(R.id.txtAddress);
        txtDlNumber      = findViewById(R.id.txtDlNumber);
        txtVehicleNumber = findViewById(R.id.txtVehicleNumber);
        txtStatus        = findViewById(R.id.txtStatus);

        // Get driver object from Intent
        DriverParameter driver = (DriverParameter) getIntent().getSerializableExtra("DRIVER");

        if (driver != null) {
            // Set values to the text views
            txtDriverName.setText(driver.getFirstName() + " " + driver.getLastName());
            txtPhone.setText("Phone: " + driver.getPhone());
            txtEmail.setText("Email: " + driver.getEmail());
            txtAddress.setText("Address: " + driver.getAdress());
            txtDlNumber.setText("DL Number: " + driver.getDlNumber());
            txtVehicleNumber.setText("Vehicle No: " + driver.getVehicleNumber());
            txtStatus.setText("Status: " + driver.getStatus());
        } else {
            Toast.makeText(this, "No driver data received", Toast.LENGTH_SHORT).show();
            finish(); // Optional: Close activity if no data
        }
    }
}
