package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private final List<Budget> budgets;

    public NotificationAdapter(List<Budget> budgets) {
        this.budgets = budgets;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Budget budget = budgets.get(position);
        String categoryName = new BudgetDAO(holder.itemView.getContext()).getCategoryNameById(budget.getCategoryId());
        holder.tvNameCategoryNotification.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameCategoryNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategoryNotification = itemView.findViewById(R.id.tvNameCategoryNotification);
        }
    }
}