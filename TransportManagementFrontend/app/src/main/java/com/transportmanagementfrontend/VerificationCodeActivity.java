package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText verificationCodeInput;
    private Button submitButton;
    private TextView emailTextView;
    private String email; // Store the email passed from ForgotPasswordActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        // Initialize views
        verificationCodeInput = findViewById(R.id.verificationCodeInput);
        submitButton = findViewById(R.id.submitButton);
        emailTextView = findViewById(R.id.emailTextView);  // Updated reference

        // Get email from the Intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        emailTextView.setText("Code sent to: " + email);  // Display email

        // Set click listener for the Submit button
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    // Method to handle "Submit" button logic: validate input and call API to verify the code
    private void handleSubmit() {
        String code = verificationCodeInput.getText().toString().trim();

        // Validate that the code field is not empty
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(VerificationCodeActivity.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call the API to verify the code
        verifyCode(email, code);
    }

    // Method to call the backend API for code verification
    private void verifyCode(String email, String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                //.baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/") // Replace with your API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<String> call = apiService.verifyCode(email, code);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VerificationCodeActivity.this, "Code verified!", Toast.LENGTH_SHORT).show();

                    // Redirect to ChangePasswordActivity, passing the email along
                    Intent intent = new Intent(VerificationCodeActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerificationCodeActivity.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(VerificationCodeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
