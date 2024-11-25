package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ItemTransaction;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Model.ItemTransaction;

import java.util.List;

public class ItemAdapterTransaction extends RecyclerView.Adapter<ItemAdapterTransaction.ItemViewHolder> {

    private List<ItemTransaction> itemList;

    public ItemAdapterTransaction(List<ItemTransaction> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemTransaction item = itemList.get(position);
        holder.tvCategory.setText(item.getCategory());
        holder.tvDescription.setText(item.getDescription());
        holder.tvPrice.setText(item.getPrice());
        holder.tvTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvDescription, tvPrice, tvTime;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
