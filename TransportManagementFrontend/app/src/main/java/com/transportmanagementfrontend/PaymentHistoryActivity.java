package com.transportmanagementfrontend;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPayments;
    private PaymentAdapter paymentAdapter;
    private List<PaymentParameter> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        recyclerViewPayments = findViewById(R.id.recyclerViewPayments);
        recyclerViewPayments.setLayoutManager(new LinearLayoutManager(this));

        paymentList = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(paymentList);
        recyclerViewPayments.setAdapter(paymentAdapter);

        fetchPaymentsFromBackend();
    }

    private void fetchPaymentsFromBackend() {
        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/") // Replace with actual backend URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<PaymentParameter>> call = apiService.getAllPayments();
        call.enqueue(new Callback<List<PaymentParameter>>() {
            @Override
            public void onResponse(Call<List<PaymentParameter>> call, Response<List<PaymentParameter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    paymentList.clear();
                    paymentList.addAll(response.body());
                    paymentAdapter.notifyDataSetChanged();
                    Log.d("API", "Payments fetched: " + paymentList.size());
                } else {
                    Log.e("API", "Empty or error response");
                }
            }

            @Override
            public void onFailure(Call<List<PaymentParameter>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch payments: " + t.getMessage());
            }
        });
    }
}
