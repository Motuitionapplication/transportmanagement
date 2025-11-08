package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home); // Make sure this XML includes all the <include> cards

        // === User Controls ===
        setupCard(R.id.card_user_management, R.drawable.user_management, "User Management", UserManagementActivity.class);
        setupCard(R.id.card_financial_management, R.drawable.financial_management, "Financial Management", FinancialManagementActivity.class);
        setupCard(R.id.card_vehicle_management, R.drawable.vehicle_management, "Vehicle Management", VehicleManagementActivity.class);

        // === Monitoring ===
        setupCard(R.id.card_live_tracking, R.drawable.live_tracking, "Live Tracking", LiveTrackingActivity.class);
        setupCard(R.id.card_analytics, R.drawable.analytics, "Analytics", AnalyticsActivity.class);

        // === System Settings ===
        setupCard(R.id.card_admin_profile, R.drawable.admin_profile, "Admin Profile", AdminProfileActivity.class);
        setupCard(R.id.card_dispute_management, R.drawable.dispute_management, "Dispute Management", DisputeManagementActivity.class);
        setupCard(R.id.card_complaints, R.drawable.complaints, "Complaint Section", ComplaintActivity.class);
        setupCard(R.id.card_driver_management, R.drawable.driver_management, "Driver Management", DriverManagementActivity.class);
        setupCard(R.id.card_owner_management, R.drawable.owner_management, "Owner Management", OwnerManagementActivity.class);
        setupCard(R.id.card_backup, R.drawable.backup, "Backup", BackupActivity.class);
    }

    private void setupCard(int cardId, int imageResId, String titleText, Class<?> targetActivity) {
        ViewGroup card = findViewById(cardId);
        if (card == null) return;

        ImageView icon = card.findViewById(R.id.icon);
        TextView title = card.findViewById(R.id.title);

        if (icon != null) icon.setImageResource(imageResId);
        if (title != null) title.setText(titleText);

        card.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, targetActivity)));
    }
}
