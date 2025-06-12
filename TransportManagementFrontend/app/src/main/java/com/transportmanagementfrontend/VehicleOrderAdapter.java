package com.transportmanagementfrontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleOrderAdapter extends RecyclerView.Adapter<VehicleOrderAdapter.OrderViewHolder> {

    private List<VehicleOrderParameter> orderList;

    public VehicleOrderAdapter(List<VehicleOrderParameter> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        VehicleOrderParameter order = orderList.get(position);
        holder.orderId.setText("Order ID: " + order.getOrderId());
        holder.vehicleNo.setText("Vehicle No: " + order.getVehicleNo());
        holder.pickup.setText("Pickup: " + order.getPickup());
        holder.drop.setText("Drop: " + order.getDrop());
        holder.distance.setText("Distance: " + order.getDistance() + " km");
        holder.price.setText("Price: ₹" + order.getPrice());
        holder.availability.setText("Availability: " + (order.isAvailability() ? "Available" : "Not Available"));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, vehicleNo, pickup, drop, distance, price, availability;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            vehicleNo = itemView.findViewById(R.id.vehicleNo);
            pickup = itemView.findViewById(R.id.pickup);
            drop = itemView.findViewById(R.id.drop);
            distance = itemView.findViewById(R.id.distance);
            price = itemView.findViewById(R.id.price);
            availability = itemView.findViewById(R.id.availability);
        }
    }
}
