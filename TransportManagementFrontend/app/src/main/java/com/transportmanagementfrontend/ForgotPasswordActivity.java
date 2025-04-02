package com.transportmanagementfrontend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button nextButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);  // Ensure layout file is correct

        // Initialize views
        emailEditText = findViewById(R.id.email);
        nextButton = findViewById(R.id.nextButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending verification code...");

        // Set click listener for the Next button
        nextButton.setOnClickListener(v -> handleNext());
    }

    // Method to handle "Next" button logic (Validate email and call API)
    private void handleNext() {
        String email = emailEditText.getText().toString().trim();

        // Validate for empty email field
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email validation passed, now send verification code via API call
        sendVerificationCode(email);
    }

    // Method to send the verification code to the provided email via API call
    private void sendVerificationCode(String email) {
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/") // Replace with your actual API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<String> call = apiService.sendVerificationCode(email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Verification code sent to " + email, Toast.LENGTH_SHORT).show();

                    // Redirect to VerificationCodeActivity and pass the email along
                    Intent intent = new Intent(ForgotPasswordActivity.this, VerificationCodeActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email not found or error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
