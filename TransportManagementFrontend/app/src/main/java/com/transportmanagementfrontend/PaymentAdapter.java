package com.transportmanagementfrontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<PaymentParameter> paymentList;

    public PaymentAdapter(List<PaymentParameter> paymentList) {
        this.paymentList = paymentList;
    }

    public void updatePayments(List<PaymentParameter> newList) {
        this.paymentList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentParameter payment = paymentList.get(position);

        holder.txtDate.setText("Date: " + payment.getDate());
        holder.txtTransactionId.setText("Transaction ID: " + payment.getTransactionId());
        holder.txtPickupPin.setText("Pickup Pin: " + payment.getPickupPin());
        holder.txtDropPin.setText("Drop Pin: " + payment.getDropPin());
        holder.txtCity.setText("City: " + payment.getCity());
        holder.txtDistance.setText("Distance: " + payment.getDistance() + " km");
        holder.txtPaymentMode.setText("Mode: " + payment.getPaymentMode());
        // Format amount nicely
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String formattedAmount = formatter.format(payment.getAmount());
        holder.txtAmount.setText("Amount: " + formattedAmount);
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtTransactionId, txtPickupPin, txtDropPin,
                txtCity, txtDistance, txtPaymentMode, txtAmount;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtTransactionId = itemView.findViewById(R.id.txtTransactionId);
            txtPickupPin = itemView.findViewById(R.id.txtPickupPin);
            txtDropPin = itemView.findViewById(R.id.txtDropPin);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtPaymentMode = itemView.findViewById(R.id.txtPaymentMode);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }

}
