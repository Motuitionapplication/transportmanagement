package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String selectedRole = null; // Store the selected role
    private boolean isRegistering = false; // Track if the user is registering or logging in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the popup overlay and container
        FrameLayout popupOverlay = findViewById(R.id.popupOverlay);
        LinearLayout popupContainer = findViewById(R.id.popupContainer);
        TextView popupTitle = findViewById(R.id.popupTitle); // Find the title text view

        // Find role buttons inside the popup overlay
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnDriver = findViewById(R.id.btnDriver);
        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnOwner = findViewById(R.id.btnOwner);

        // Find main buttons (LOGIN and REGISTER)
        Button btnLoginMain = findViewById(R.id.btnLogin);
        Button btnRegisterMain = findViewById(R.id.btnRegister);

        // LOGIN button click event
        btnLoginMain.setOnClickListener(v -> {
            isRegistering = false; // Set flag to false (user is logging in)
            btnAdmin.setVisibility(View.VISIBLE);  // Show Admin for LOGIN
            popupTitle.setText("LOGIN AS:");  // Change popup title
            popupOverlay.setVisibility(View.VISIBLE); // Show popup
        });

        // REGISTER button click event
        btnRegisterMain.setOnClickListener(v -> {
            isRegistering = true; // Set flag to true (user is registering)
            btnAdmin.setVisibility(View.GONE);  // Hide Admin for REGISTER
            popupTitle.setText("REGISTER AS:");  // Change popup title
            popupOverlay.setVisibility(View.VISIBLE); // Show popup
        });

        // Role selection listener
        View.OnClickListener roleClickListener = v -> {
            selectedRole = ((Button) v).getText().toString();

            if (isRegistering) {
                showToast("Registering As: " + selectedRole);
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("ROLE", selectedRole);
                startActivity(intent);
            } else {
                showToast("Logging In As: " + selectedRole);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("ROLE", selectedRole);
                startActivity(intent);
            }
        };

        // Assign listener to each role button
        btnAdmin.setOnClickListener(roleClickListener);
        btnDriver.setOnClickListener(roleClickListener);
        btnCustomer.setOnClickListener(roleClickListener);
        btnOwner.setOnClickListener(roleClickListener);

        // Click outside popup to close
        popupOverlay.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));

        // Prevent closing popup when clicking inside
        popupContainer.setOnClickListener(v -> {
            // Do nothing to prevent dismissing
        });
    }

    /**
     * Displays a toast message.
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
