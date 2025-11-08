package com.transportmanagementfrontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList;
    private OnApproveClickListener listener;

    public interface OnApproveClickListener {
        void onApproveClick(UserModel user);
    }

    public UserAdapter(List<UserModel> userList, OnApproveClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtRole;
        Button btnApprove;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtRole = itemView.findViewById(R.id.txtRole);
            btnApprove = itemView.findViewById(R.id.btnApprove);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.txtUsername.setText(user.getUsername());
        holder.txtRole.setText(user.getRole());

        holder.btnApprove.setEnabled(!user.isApproved());
        holder.btnApprove.setText(user.isApproved() ? "Approved" : "Approve");

        holder.btnApprove.setOnClickListener(v -> {
            if (!user.isApproved()) {
                listener.onApproveClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
