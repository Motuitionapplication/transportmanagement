package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextView roleTextView;
    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure the layout file is correct

        // Get the selected role from the intent
        selectedRole = getIntent().getStringExtra("ROLE");

        // Initialize roleTextView
        roleTextView = findViewById(R.id.roleTextView);
        if (selectedRole != null) {
            roleTextView.setText("Logged In As: " + selectedRole);
            Toast.makeText(this, "Logged In As: " + selectedRole, Toast.LENGTH_SHORT).show();
        } else {
            roleTextView.setText("Logged In As: Unknown");
        }

        // Set click listeners for buttons
        findViewById(R.id.loginButton).setOnClickListener(v -> handleLogin());
        findViewById(R.id.forgotPassword).setOnClickListener(v -> handleForgotPassword());
        findViewById(R.id.registerButton).setOnClickListener(v -> handleRegister());
    }

    // Method to handle login logic
    private void handleLogin() {
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_LONG).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                // .baseUrl("http://gkct1transport.us-east-1.elasticbeanstalk.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        CustomerLoginRequest loginRequest = new CustomerLoginRequest(username, password);
        Call<CustomerLoginResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<CustomerLoginResponse>() {
            @Override
            public void onResponse(Call<CustomerLoginResponse> call, Response<CustomerLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(LoginActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.putExtra("USERNAME", username);
//                    intent.putExtra("ROLE", selectedRole);
//                    intent.putExtra("FIRSTNAME", response.body().getUser().getFirstName());
//
//                    startActivity(intent);
//                    finish();

                    // Redirect user based on role
                    Intent intent = getHomePageIntent(selectedRole);
                    if (intent != null) {
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("ROLE", selectedRole);
                        intent.putExtra("FIRST_NAME", response.body().getUser().getFirstName());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Intent getHomePageIntent(String role) {
        Intent intent = null;
        if (role != null) {
            switch (role.toLowerCase()) {
                case "customer":
                    intent = new Intent(LoginActivity.this, CustomerHomeActivity.class);
                    break;
                case "owner":
                    intent = new Intent(LoginActivity.this, OwnerHomeActivity.class);
                    break;
                case "driver":
                    intent = new Intent(LoginActivity.this, DriverHomeActivity.class);
                    break;
                case "admin":
                    intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "Error: Invalid Role!", Toast.LENGTH_SHORT).show();
            }
        }
        return intent;
    }

    private void handleForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void handleRegister() {
        // Redirect to MainActivity instead of RegisterActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}