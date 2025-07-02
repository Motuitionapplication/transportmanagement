package com.transportmanagementfrontend;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private List<Vehicle> vehicleList;

    public VehicleAdapter(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        Vehicle v = vehicleList.get(position);
        holder.txtVehicleNumber.setText("Vehicle No: " + v.getVehicleNumber());
        holder.txtStatus.setText("Status: " + v.getStatus());
        holder.txtOwnerName.setText("Owner: " + v.getOwnerName());
        holder.txtDate.setText("Date: " + v.getDate());

        // Color status
        switch (v.getStatus().toLowerCase()) {
            case "available":
                holder.txtStatus.setTextColor(Color.parseColor("#4CAF50")); // Green
                break;
            case "unavailable":
                holder.txtStatus.setTextColor(Color.parseColor("#F44336")); // Red
                break;
            case "reserved":
                holder.txtStatus.setTextColor(Color.parseColor("#FF9800")); // Orange
                break;
            default:
                holder.txtStatus.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView txtVehicleNumber, txtStatus, txtOwnerName, txtDate;

        public VehicleViewHolder(View itemView) {
            super(itemView);
            txtVehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtOwnerName = itemView.findViewById(R.id.txtOwnerName);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
