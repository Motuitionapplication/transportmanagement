package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceCenterActivity extends AppCompatActivity {
    Button confirm1, delete1, confirm2, delete2, confirm3, delete3;
    LinearLayout btnAddCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center); // Replace with your actual XML filename

        // Card 1 buttons
        confirm1 = findViewById(R.id.serviceCentreBox).findViewWithTag("confirm1");
        delete1 = findViewById(R.id.serviceCentreBox).findViewWithTag("delete1");

        // Card 2 buttons
        confirm2 = findViewById(R.id.serviceCentreBox).findViewWithTag("confirm2");
        delete2 = findViewById(R.id.serviceCentreBox).findViewWithTag("delete2");

        // Card 3 buttons
        confirm3 = findViewById(R.id.serviceCentreBox).findViewWithTag("confirm3");
        delete3 = findViewById(R.id.serviceCentreBox).findViewWithTag("delete3");

        // Add centre button
        btnAddCentre = findViewById(R.id.btnAddCentre);

        // Listeners
        confirm1.setOnClickListener(v -> showToast("Moto Garage confirmed"));
        delete1.setOnClickListener(v -> showToast("Delete requested for Moto Garage"));

        confirm2.setOnClickListener(v -> showToast("Indian Oil confirmed"));
        delete2.setOnClickListener(v -> showToast("Delete requested for Indian Oil"));

        confirm3.setOnClickListener(v -> showToast("MRF Tyre Shop confirmed"));
        delete3.setOnClickListener(v -> showToast("Delete requested for MRF Tyre Shop"));

        btnAddCentre.setOnClickListener(v -> showToast("Add Centre clicked"));
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}


