package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout; // FrameLayout instead of LinearLayout for overlay
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button selectedButton = null; // Track the selected button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_main);

        // Find the "Get Started" button by its ID
        Button getStartedButton = findViewById(R.id.btnLogin);

        // Find the popup overlay layout (FrameLayout should be used for overlay)
        FrameLayout popupOverlay = findViewById(R.id.popupOverlay);

        // Set an OnClickListener for the "Get Started" button
        getStartedButton.setOnClickListener(v -> {
            // Show the popup overlay
            popupOverlay.setVisibility(View.VISIBLE);
        });

        // Find the buttons inside the popup overlay
        Button btnVendor = findViewById(R.id.btnVendor);
        Button btnDriver = findViewById(R.id.btnDriver);
        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnOwner = findViewById(R.id.btnOwner);

        // OnClickListener for the role buttons
        View.OnClickListener roleClickListener = v -> {
            // Get the text of the button clicked
            String role = ((Button) v).getText().toString();

            // Example: Show role as a toast message
            showToast("Logging In As: " + role);

            // Redirect to Login page with selected role
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("ROLE", role); // Pass the selected role to the LoginActivity
            startActivity(intent);

            // Change the background color of the selected button and revert the others
            if (selectedButton != null) {
                // Reset the previously selected button's background
                selectedButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));
            }

            // Set the new selected button color
            selectedButton = (Button) v;
            selectedButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_orange_light)); // Change to selected color
        };

        // Assign listeners to the role buttons
        btnVendor.setOnClickListener(roleClickListener);
        btnDriver.setOnClickListener(roleClickListener);
        btnCustomer.setOnClickListener(roleClickListener);
        btnOwner.setOnClickListener(roleClickListener);

        // Set an OnClickListener to dismiss the popup when clicking outside the popup container
        popupOverlay.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));

        // Prevent the popup overlay itself from dismissing when clicked inside the container
        LinearLayout popupContainer = findViewById(R.id.popupContainer); // This should be the ID of the layout inside the popup
        popupContainer.setOnClickListener(v -> {
            // Do nothing to prevent dismissing when clicking inside the popup
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
