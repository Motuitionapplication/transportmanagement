package com.transportmanagementfrontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private List<Complaint> complaints;

    public ComplaintAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        Complaint c = complaints.get(position);
        holder.txtUsername.setText(c.getUsername());
        holder.txtMessage.setText(c.getMessage());
        holder.txtDate.setText(c.getDate());
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtMessage, txtDate;

        ComplaintViewHolder(View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
