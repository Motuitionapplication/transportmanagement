package com.transportmanagementfrontend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintSectionActivity extends AppCompatActivity {
        Button whatsappButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complaint); // Ensure this matches your XML filename

            whatsappButton = findViewById(R.id.whatsapp_button);

            whatsappButton.setOnClickListener(v -> openWhatsApp());
        }

        private void openWhatsApp() {
            String phoneNumber = "+91XXXXXXXXXX"; // Replace with your support number
            String message = "Hello, I want to raise a complaint.";
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message)));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


