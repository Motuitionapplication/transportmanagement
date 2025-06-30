package com.transportmanagementfrontend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverHomeActivity extends AppCompatActivity {

    ImageButton btnTransitCustomerInfo, btnServiceCenter, btnComplaintSection,
            btnNotifications, btnVendorTracking, btnDeliveryConfirmation,
            btnWhatsApp, btnCall;
    private ApiService apiService;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home); // change if your XML filename is different

        // Initialize buttons
        welcomeText = findViewById(R.id.welcomeText);
        btnTransitCustomerInfo = findViewById(R.id.btnTransitCustomerInfo);
        btnServiceCenter = findViewById(R.id.btnServiceCenter);
        btnComplaintSection = findViewById(R.id.btnComplaintSection);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnVendorTracking = findViewById(R.id.btnVendorTracking);
        btnDeliveryConfirmation = findViewById(R.id.btnDeliveryConfirmation);
        btnWhatsApp = findViewById(R.id.btnWhatsApp);
        btnCall = findViewById(R.id.btnCall);

        // Get user details
        String firstName = getIntent().getStringExtra("FIRST_NAME");
        welcomeText.setText(firstName != null && !firstName.isEmpty() ? "Hello, " + firstName + "!" : "");

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                //.baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Set click listeners
        btnTransitCustomerInfo.setOnClickListener(v -> startActivity(new Intent(this, TransitCustomerInfoActivity.class)));
        btnServiceCenter.setOnClickListener(v -> startActivity(new Intent(this, ServiceCenterActivity.class)));
        btnComplaintSection.setOnClickListener(v -> startActivity(new Intent(this, ComplaintSectionActivity.class)));
        btnNotifications.setOnClickListener(v -> startActivity(new Intent(this, NotificationsActivity.class)));
        btnVendorTracking.setOnClickListener(v -> startActivity(new Intent(this, VendorTrackingActivity.class)));
        btnDeliveryConfirmation.setOnClickListener(v -> startActivity(new Intent(this, DeliveryConfirmationActivity.class)));

        // WhatsApp Support
        btnWhatsApp.setOnClickListener(v -> {
            String phoneNumber = "+91XXXXXXXXXX"; // Replace with your WhatsApp number
            String url = "https://wa.me/" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Call Support
        btnCall.setOnClickListener(v -> {
            String phone = "tel:+91XXXXXXXXXX"; // Replace with your support number
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(phone));
            startActivity(intent);
        });
    }
}
