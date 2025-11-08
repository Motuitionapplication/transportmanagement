package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComplaintActivity extends AppCompatActivity {

    RecyclerView recyclerComplaints;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);

        recyclerComplaints = findViewById(R.id.recyclerComplaints);
        recyclerComplaints.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);

        fetchComplaints();
    }

    private void fetchComplaints() {
        api.getAllComplaints().enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ComplaintAdapter adapter = new ComplaintAdapter(response.body());
                    recyclerComplaints.setAdapter(adapter);
                } else {
                    Toast.makeText(ComplaintActivity.this, "No complaints found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                Toast.makeText(ComplaintActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
