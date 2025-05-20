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
        setContentView(R.layout.activity_login);

        selectedRole = getIntent().getStringExtra("ROLE");

        roleTextView = findViewById(R.id.roleTextView);
        if (selectedRole != null) {
            roleTextView.setText("Logged In As: " + selectedRole);
        } else {
            roleTextView.setText("Logged In As: Unknown");
        }

        findViewById(R.id.loginButton).setOnClickListener(v -> handleLogin());
        findViewById(R.id.forgotPassword).setOnClickListener(v -> handleForgotPassword());
        findViewById(R.id.registerButton).setOnClickListener(v -> handleRegister());
    }

    private void handleLogin() {
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String baseUrl = selectedRole.equalsIgnoreCase("owner")
                ? "http://10.0.2.2:8080/api/owners/"
                : "http://10.0.2.2:8080/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        if (selectedRole.equalsIgnoreCase("owner")) {
            OwnerLoginRequest loginRequest = new OwnerLoginRequest(username, password);
            Call<OwnerLoginResponse> call = apiService.loginOwner(loginRequest);

            call.enqueue(new Callback<OwnerLoginResponse>() {
                @Override
                public void onResponse(Call<OwnerLoginResponse> call, Response<OwnerLoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent(LoginActivity.this, OwnerHomeActivity.class);
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("ROLE", selectedRole);
                        intent.putExtra("FIRST_NAME", response.body().getOwner().getFirstName());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Owner login failed. Check credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OwnerLoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            CustomerLoginRequest loginRequest = new CustomerLoginRequest(username, password);
            Call<CustomerLoginResponse> call = apiService.loginUser(loginRequest);

            call.enqueue(new Callback<CustomerLoginResponse>() {
                @Override
                public void onResponse(Call<CustomerLoginResponse> call, Response<CustomerLoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = getHomePageIntent(selectedRole);
                        if (intent != null) {
                            intent.putExtra("USERNAME", username);
                            intent.putExtra("ROLE", selectedRole);
                            intent.putExtra("FIRST_NAME", response.body().getUser().getFirstName());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Customer login failed. Check credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CustomerLoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private Intent getHomePageIntent(String role) {
        if (role == null) return null;

        switch (role.toLowerCase()) {
            case "customer":
                return new Intent(LoginActivity.this, CustomerHomeActivity.class);
            case "owner":
                return new Intent(LoginActivity.this, OwnerHomeActivity.class);
            case "driver":
                return new Intent(LoginActivity.this, DriverHomeActivity.class);
            case "admin":
                return new Intent(LoginActivity.this, AdminHomeActivity.class);
            default:
                return null;
        }
    }

    private void handleForgotPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    private void handleRegister() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
