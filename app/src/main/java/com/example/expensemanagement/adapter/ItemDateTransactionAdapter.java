package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ItemDateTransaction;

import com.example.expensemanagement.R;


import java.util.List;

public class ItemDateTransactionAdapter extends RecyclerView.Adapter<ItemDateTransactionAdapter.ItemViewHolder> {

    private List<ItemDateTransaction> itemList;

    public ItemDateTransactionAdapter(List<ItemDateTransaction> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date_transaction, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemDateTransaction item = itemList.get(position);
          holder.tvDate.setText(item.getDate());
          holder.tvPriceDate.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvPriceDate , tvDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriceDate = itemView.findViewById(R.id.tvPriceDate);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
