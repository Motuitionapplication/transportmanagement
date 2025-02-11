package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, ageEditText, addressEditText, emailEditText, phoneEditText, passwordEditText;
    private Button createAccountButton;
    private String selectedRole; // Store the role selected on the previous screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Retrieve the selected role from the intent.
        selectedRole = getIntent().getStringExtra("ROLE");

        // Ensure role is passed
        if (selectedRole == null || selectedRole.trim().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Error: Role not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set click listener for the Create Account button
        createAccountButton.setOnClickListener(v -> {
            String firstName = getSafeText(firstNameEditText);
            String lastName = getSafeText(lastNameEditText);
            String age = getSafeText(ageEditText);
            String address = getSafeText(addressEditText);
            String email = getSafeText(emailEditText);
            String phone = getSafeText(phoneEditText);
            String password = getSafeText(passwordEditText);

            // Validate that no field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || address.isEmpty() ||
                    email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the API for registration
            registerUser(firstName, lastName, age, address, email, phone, password, selectedRole);
        });
    }

    private String getSafeText(EditText editText) {
        return (editText != null && editText.getText() != null) ? editText.getText().toString().trim() : "";
    }

    private void registerUser(String firstName, String lastName, String age, String address, String email,
                              String phone, String password, String role) {
        // Enable lenient JSON parsing
        Gson gson = new GsonBuilder()
                .setLenient()  // Allow minor JSON formatting issues
                .create();

        // Create Retrofit instance using both ScalarsConverterFactory and GsonConverterFactory.
        // IMPORTANT: ScalarsConverterFactory must be added first.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, age, address, email, phone, password, role);

        // Make the API call, now expecting a String response
        Call<String> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.errorBody() != null && response.body() != null) {
                    // Retrieve the plain string message from the response
                    String message = response.body();
                    Toast.makeText(RegisterActivity.this, "Welcome to " + selectedRole + " Page! " + message, Toast.LENGTH_SHORT).show();

                    // Redirect user to their respective home screen
                    Intent intent = getHomePageIntent(selectedRole);
                    if (intent != null) {
                        intent.putExtra("ROLE", selectedRole);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    // Log the raw error response for debugging
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API_ERROR", "Raw error response: " + errorResponse);
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Error reading error response", e);
                    }
                    Toast.makeText(RegisterActivity.this, "Registration Failed. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_FAILURE", "Request failed", t);
            }
        });
    }


    private Intent getHomePageIntent(String role) {
        Intent intent = null;
        switch (role.toLowerCase()) {
            case "customer":
                intent = new Intent(RegisterActivity.this, CustomerHomeActivity.class);
                break;
            case "owner":
                intent = new Intent(RegisterActivity.this, OwnerHomeActivity.class);
                break;
            case "driver":
                intent = new Intent(RegisterActivity.this, DriverHomeActivity.class);
                break;
            default:
                Toast.makeText(RegisterActivity.this, "Error: Invalid Role!", Toast.LENGTH_SHORT).show();
        }
        return intent;
    }
}
