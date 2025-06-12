package com.transportmanagementfrontend;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VehicleOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_order);

        recyclerView = findViewById(R.id.recyclerViewVehicleOrders); // Make sure this ID exists in your XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchOrderData();
    }

    private void fetchOrderData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<VehicleOrderParameter>> call = apiService.getVehicleOrders();

        call.enqueue(new Callback<List<VehicleOrderParameter>>() {
            @Override
            public void onResponse(Call<List<VehicleOrderParameter>> call, Response<List<VehicleOrderParameter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new VehicleOrderAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No vehicle orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VehicleOrderParameter>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
