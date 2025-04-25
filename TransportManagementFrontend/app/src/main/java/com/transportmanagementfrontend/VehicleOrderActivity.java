package com.transportmanagementfrontend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VehicleOrderActivity extends AppCompatActivity {

    private TextView txtOrderId,
            txtVehicleNo,
            txtPickup,
            txtDrop,
            txtDistance,
            txtPrice,
            txtAvailability;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_order);

        // Initialize UI components
        txtOrderId      = findViewById(R.id.txtOrderId);
        txtVehicleNo    = findViewById(R.id.txtVehicleNo);
        txtPickup       = findViewById(R.id.txtPickup);
        txtDrop         = findViewById(R.id.txtDrop);
        txtDistance     = findViewById(R.id.txtDistance);
        txtPrice        = findViewById(R.id.txtPrice);
        txtAvailability = findViewById(R.id.txtAvailability);

        // Get order details from Intent
        OrderParameter order = (OrderParameter) getIntent().getSerializableExtra("ORDER");

        if (order != null) {
            txtOrderId.setText("Order ID: " + order.getOrderId());
            txtVehicleNo.setText("Vehicle No: " + order.getVehicleNumber());
            txtPickup.setText("Pickup: " + order.getPickupLocation());
            txtDrop.setText("Drop: " + order.getDropLocation());
            txtDistance.setText("Distance: " + order.getDistance() + " km");
            txtPrice.setText("Price: ₹" + order.getPrice());
            txtAvailability.setText("Availability: " +
                    (order.isAvailable() ? "Available" : "Not Available"));
        }
    }
}
