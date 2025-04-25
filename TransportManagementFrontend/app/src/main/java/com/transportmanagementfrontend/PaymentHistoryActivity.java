package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentHistoryActivity extends AppCompatActivity {

    private TextView txtHeader,
            txtDate,
            txtTransactionId,
            txtPickup,
            txtDrop,
            txtFare,
            txtDriverName,
            txtVehicleNo,
            watermarkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        // Initialize UI components
        txtHeader         = findViewById(R.id.txtHeader);
        txtDate           = findViewById(R.id.txtDate);
        txtTransactionId  = findViewById(R.id.txtTransactionId);
        txtPickup         = findViewById(R.id.txtPickup);
        txtDrop           = findViewById(R.id.txtDrop);
        txtFare           = findViewById(R.id.txtFare);
        txtDriverName     = findViewById(R.id.txtDriverName);
        txtVehicleNo      = findViewById(R.id.txtVehicleNo);
        watermarkTextView = findViewById(R.id.watermarkTextView);

        // Get payment details from Intent
        PaymentParameter payment = (PaymentParameter) getIntent().getSerializableExtra("PAYMENT");

        if (payment != null) {
            txtDate.setText("Date: " + payment.getDate());
            txtTransactionId.setText("Transaction ID: " + payment.getTransactionId());
            txtPickup.setText("Pickup: " + payment.getPickup());
            txtDrop.setText("Drop: " + payment.getDrop());
            txtFare.setText("Fare: ₹" + payment.getFare());
            txtDriverName.setText("Driver Name: " + payment.getDriverName());
            txtVehicleNo.setText("Vehicle No: " + payment.getVehicleNo());
        }
    }
}
