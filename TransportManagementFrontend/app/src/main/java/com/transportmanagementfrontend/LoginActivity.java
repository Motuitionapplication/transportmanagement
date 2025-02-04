package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure the layout file is correct

        // Set click listeners for buttons (if any)
        findViewById(R.id.loginButton).setOnClickListener(v -> handleLogin());
        findViewById(R.id.forgotPassword).setOnClickListener(v -> handleForgotPassword());
        findViewById(R.id.newUserText).setOnClickListener(v -> handleNewUser());
        findViewById(R.id.registerButton).setOnClickListener(v -> handleRegister());
    }

    // Method to handle login logic
    private void handleLogin() {
        // Create Retrofit instance and API service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/") // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Create a login request object (assuming you have a loginRequest object)
        LoginRequest loginRequest = new LoginRequest("test", "test");
        Call<LoginResponse> call = apiService.loginUser(loginRequest);

        // Enqueue the request asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.isSuccess()) {
                    // Handle successful response
                    // Process the login response
                    Toast.makeText(LoginActivity.this, loginResponse.getStatus(), Toast.LENGTH_SHORT).show();
                    // You can now navigate to a new screen or perform additional actions
                } else {
                    // Handle failure response (e.g., invalid credentials)
                    Toast.makeText(LoginActivity.this, loginResponse.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle failure (network error, server issue, etc.)
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle forgot password logic (Redirect to ForgotPasswordActivity)
    private void handleForgotPassword() {
        // Redirect to ForgotPasswordActivity
        // Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        // startActivity(intent);
    }

    // Method to handle new user (Navigating to RegisterActivity)
    private void handleNewUser() {
        // Navigate to RegisterActivity for new user registration
        // Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        // startActivity(intent);
    }

    // Method to handle register button logic (Navigating to RegisterActivity)
    private void handleRegister() {
        // Navigate to RegisterActivity for user registration
        // Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        // startActivity(intent);
    }
}
