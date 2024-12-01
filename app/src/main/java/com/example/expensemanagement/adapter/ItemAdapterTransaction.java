package com.example.expensemanagement.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.text.DecimalFormat;
import java.util.List;

public class ItemAdapterTransaction extends RecyclerView.Adapter<ItemAdapterTransaction.ItemViewHolder> {

    private List<Transaction> itemListTransaction;

    public ItemAdapterTransaction(List<Transaction> itemListTransaction) {
        this.itemListTransaction = itemListTransaction;
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
        Transaction transaction = itemListTransaction.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedTotalSpent = formatter.format(transaction.getAmount());

        // Hiển thị thông tin
        holder.tvCategory.setText(transaction.getCategoryName());
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvPrice.setText(formattedTotalSpent);
        holder.tvTime.setText(transaction.getDate());

        // Kiểm tra type và thay đổi màu sắc và dấu trừ cho tvPrice
        if ("expense".equalsIgnoreCase(transaction.getType())) {
            holder.tvPrice.setTextColor(Color.RED);  // Đặt màu đỏ cho tvPrice
            holder.tvPrice.setText("-" + formattedTotalSpent + "đ");  // Thêm dấu trừ vào trước số tiền
        } else if ("income".equalsIgnoreCase(transaction.getType())) {
            holder.tvPrice.setTextColor(Color.GREEN);  // Đặt màu đỏ cho tvPrice
            holder.tvPrice.setText("+" + formattedTotalSpent + "đ");  // Thêm dấu trừ vào trước số tiền
        }
        else {
            holder.tvPrice.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return itemListTransaction.size();
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
