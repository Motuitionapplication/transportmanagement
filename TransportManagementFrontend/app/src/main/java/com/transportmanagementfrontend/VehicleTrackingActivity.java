package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VehicleTrackingActivity extends AppCompatActivity {

    private TextView title;
    private EditText pickupEditText, deliverEditText;
    private ImageView mapImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_tracking);

        // Initialize views
        title = findViewById(R.id.title);
        pickupEditText = findViewById(R.id.et_pickup);
        deliverEditText = findViewById(R.id.et_deliver);
        mapImageView = findViewById(R.id.map_image);

        // Set any logic here (example: getting pickup and delivery input)
        // Optional: Disable editing if fields are static display
        pickupEditText.setEnabled(false);
        deliverEditText.setEnabled(false);
    }
}
