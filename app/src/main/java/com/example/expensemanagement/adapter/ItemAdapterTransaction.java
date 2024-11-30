package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        String type = item.getType();
        holder.tvDescription.setText(item.getDescription());
        holder.tvPrice.setText(item.getPrice());
        holder.tvTime.setText(item.getTime());

        String category = item.getCategory();
        if (type != null) {
            // Thay đổi màu nền của item
            switch (type.toLowerCase()) {
                case "expense":
//                    holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorFood));
                    holder.ivIcon.setImageResource(R.drawable.icon_item_red);
                    holder.tvPrice.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.color_expense));
                    break;
                case "income":
                    holder.ivIcon.setImageResource(R.drawable.icon_item);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvDescription, tvPrice, tvTime;
        ImageView ivIcon;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
