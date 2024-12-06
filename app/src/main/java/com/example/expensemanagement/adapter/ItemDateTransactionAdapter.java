package com.example.expensemanagement.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ItemDateTransaction;
import com.example.expensemanagement.R;

import java.text.DecimalFormat;
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

        // Set date
        holder.tvDate.setText(item.getDate());

        // Format price with comma separator
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedPrice = formatter.format(item.getPrice());
        holder.tvPriceDate.setText(formattedPrice);

        // Set text color based on type
        if ("expense".equalsIgnoreCase(item.getType())) {
            holder.tvPriceDate.setTextColor(Color.RED);
        } else if ("income".equalsIgnoreCase(item.getType())) {
            holder.tvPriceDate.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvPriceDate, tvDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriceDate = itemView.findViewById(R.id.tvPriceDate);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
