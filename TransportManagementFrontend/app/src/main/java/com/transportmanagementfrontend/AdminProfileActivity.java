package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminProfileActivity extends AppCompatActivity {

    EditText edtAdminUsername, edtAdminPassword;
    EditText edtSubAdminUsername, edtSubAdminPassword;
    Button btnUpdateAdmin, btnCreateSubAdmin;

    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        edtAdminUsername = findViewById(R.id.edtAdminUsername);
        edtAdminPassword = findViewById(R.id.edtAdminPassword);
        edtSubAdminUsername = findViewById(R.id.edtSubAdminUsername);
        edtSubAdminPassword = findViewById(R.id.edtSubAdminPassword);
        btnUpdateAdmin = findViewById(R.id.btnUpdateAdmin);
        btnCreateSubAdmin = findViewById(R.id.btnCreateSubAdmin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);

        loadAdmin();

        btnUpdateAdmin.setOnClickListener(v -> updateAdmin());
        btnCreateSubAdmin.setOnClickListener(v -> createSubAdmin());
    }

    private void loadAdmin() {
        api.getAdminProfile().enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminRequest admin = response.body();
                    edtAdminUsername.setText(admin.getUsername());
                    edtAdminPassword.setText(admin.getPassword());
                }
            }

            @Override
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                Toast.makeText(AdminProfileActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAdmin() {
        String username = edtAdminUsername.getText().toString();
        String password = edtAdminPassword.getText().toString();

        AdminRequest request = new AdminRequest(username, password);

        api.updateAdminProfile(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AdminProfileActivity.this, "Admin updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createSubAdmin() {
        String username = edtSubAdminUsername.getText().toString();
        String password = edtSubAdminPassword.getText().toString();

        AdminRequest request = new AdminRequest(username, password);

        api.createSubAdmin(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AdminProfileActivity.this, "Sub-admin created", Toast.LENGTH_SHORT).show();
                edtSubAdminUsername.setText("");
                edtSubAdminPassword.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminProfileActivity.this, "Creation failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
