package com.transportmanagementfrontend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationsActivity extends AppCompatActivity {

        private TextView customerReply, ownerReply, companyReply;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notifications); // Replace with your actual layout file name

            // Initialize reply text views
            customerReply = findViewById(R.id.customer_reply);
            ownerReply = findViewById(R.id.owner_reply);
            companyReply = findViewById(R.id.company_reply);

            // If you're using the same ID for all "Reply" TextViews, consider changing them to unique IDs in the XML
            // For now, set click listeners manually by accessing them through positions in the layout if IDs are same

            // Setup click listeners (optional)
            customerReply.setOnClickListener(v -> showToast("Replying to Customer"));
            ownerReply.setOnClickListener(v -> showToast("Replying to Owner"));
            companyReply.setOnClickListener(v -> showToast("Replying to Company"));
        }

        private void showToast(String message) {
            Toast.makeText(NotificationsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }


