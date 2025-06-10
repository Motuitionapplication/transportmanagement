package com.transportmanagementfrontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPayments;
    private PaymentAdapter paymentAdapter;
    private ArrayList<PaymentParameter> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        recyclerViewPayments = findViewById(R.id.recyclerViewPayments);
        recyclerViewPayments.setLayoutManager(new LinearLayoutManager(this));

        // Try to get the payment list from Intent
        paymentList = (ArrayList<PaymentParameter>) getIntent().getSerializableExtra("PAYMENT_LIST");

        if (paymentList == null) {
            // If no data passed, create dummy data for testing
            paymentList = new ArrayList<>();
            paymentList.add(new PaymentParameter("2025-06-05", "TXN12345", "560001", "560100", "Bangalore", 15.2, "Card", new BigDecimal("350.00")));
            paymentList.add(new PaymentParameter("2025-06-01", "TXN12346", "560002", "560105", "Bangalore", 10.0, "Cash", new BigDecimal("350.00")));
        }

        paymentAdapter = new PaymentAdapter(paymentList);
        recyclerViewPayments.setAdapter(paymentAdapter);

        // Simulate receiving updated data (like from Retrofit/Firebase)
        simulateDataUpdate();
    }

    // Simulate an async data update callback
    private void simulateDataUpdate() {
        // New dummy updated data list
        List<PaymentParameter> newList = new ArrayList<>();
        newList.add(new PaymentParameter("2025-06-06", "TXN99999", "560003", "560106", "Bangalore", 20.0, "UPI", new BigDecimal("350.00")));
        newList.add(new PaymentParameter("2025-06-07", "TXN88888", "560004", "560107", "Bangalore", 5.5, "Card", new BigDecimal("350.00")));

        // Call the method to update adapter's data
        onDataReceived(newList);
    }

    // Example Retrofit or Firebase callback method to update data
    public void onDataReceived(List<PaymentParameter> newList) {
        paymentAdapter.updatePayments(newList);
    }
}
