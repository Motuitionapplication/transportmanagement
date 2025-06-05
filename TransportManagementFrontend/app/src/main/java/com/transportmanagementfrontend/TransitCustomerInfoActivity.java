package com.transportmanagementfrontend;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransitCustomerInfoActivity extends AppCompatActivity {

        private Button btnStartLocation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_info);

            // Initialize views
            btnStartLocation = findViewById(R.id.btnStartLocation);

            // Handle "Start Location" button click
            btnStartLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Show a simple toast for demo
                    Toast.makeText(TransitCustomerInfoActivity.this, "Starting location...", Toast.LENGTH_SHORT).show();

                    // OPTIONAL: Launch Google Maps navigation (replace with actual coordinates or address)
                /*
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Bhubaneswar+India");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Check if the device can handle the Intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(OwnerTransitInfoActivity.this, "Google Maps not available", Toast.LENGTH_SHORT).show();
                }
                */
                }
            });

        }
    }


