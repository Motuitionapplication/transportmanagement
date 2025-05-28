package com.transportmanagementfrontend;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerHomeActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        TextView welcomeText = findViewById(R.id.welcomeText);
        ImageButton btnProfile = findViewById(R.id.btnProfile);
        ImageButton btnOtherServices = findViewById(R.id.btnOtherServices);
        ImageButton btnVehicleOrder = findViewById(R.id.btnVehicleOrder);
        ImageButton btnDriverDetails = findViewById(R.id.btnDriverDetails);
        ImageButton btnVehicleTracking = findViewById(R.id.btnVehicleTracking);
        ImageButton btnPaymentHistory = findViewById(R.id.btnPaymentHistory);
        ImageButton btnWhatsApp = findViewById(R.id.btnWhatsApp);
        ImageButton btnCall = findViewById(R.id.btnCall);

        // Get user details
        String firstName = getIntent().getStringExtra("FIRST_NAME");
        String ownerId = getIntent().getStringExtra("ownerId");  // not "OWNER_ID"
        welcomeText.setText(firstName != null && !firstName.isEmpty() ? "Hello, " + firstName + "!" : "");

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                //.baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Click Listeners for Buttons
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(OwnerHomeActivity.this, OwnerProfileActivity.class);
            intent.putExtra("ownerId", ownerId); // ✅ Pass ownerId to profile
            startActivity(intent);
        });

        btnOtherServices.setOnClickListener(view -> startActivity(new Intent(OwnerHomeActivity.this, OtherServicesActivity.class)));
        btnVehicleOrder.setOnClickListener(view -> startActivity(new Intent(OwnerHomeActivity.this, VehicleOrderActivity.class)));
        btnVehicleTracking.setOnClickListener(view -> startActivity(new Intent(OwnerHomeActivity.this, VehicleTrackingActivity.class)));
        btnPaymentHistory.setOnClickListener(view -> startActivity(new Intent(OwnerHomeActivity.this, PaymentHistoryActivity.class)));

        // Handle Driver Details Click
        btnDriverDetails.setOnClickListener(view -> showVehicleNumberDialog());

        // Customer Support Buttons (To be implemented later)
        btnWhatsApp.setOnClickListener(view -> {
            // WhatsApp functionality placeholder
        });

        btnCall.setOnClickListener(view -> {
            // Call functionality placeholder
        });
    }

    private void showVehicleNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Vehicle Number");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Fetch Details", (dialog, which) -> {
            String vehicleNumber = input.getText().toString().trim();
            if (!vehicleNumber.isEmpty()) {
                fetchDriverDetails(vehicleNumber);
            } else {
                Toast.makeText(OwnerHomeActivity.this, "Vehicle number cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void fetchDriverDetails(String vehicleNumber) {
        Call<DriverParameter> call = apiService.getDriverByVehicle(vehicleNumber);
        call.enqueue(new Callback<DriverParameter>() {
            @Override
            public void onResponse(Call<DriverParameter> call, Response<DriverParameter> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DriverParameter driver = response.body();

                    // Pass data to DriverDetailsActivity
                    Intent intent = new Intent(OwnerHomeActivity.this, DriverDetailsActivity.class);
                    intent.putExtra("DRIVER", driver);
                    startActivity(intent);
                } else {
                    Toast.makeText(OwnerHomeActivity.this, "Driver not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverParameter> call, Throwable t) {
                Toast.makeText(OwnerHomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
