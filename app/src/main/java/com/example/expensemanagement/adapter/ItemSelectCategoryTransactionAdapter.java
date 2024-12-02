package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;

import com.example.expensemanagement.sqlite_database.entities.Bank;
import com.example.expensemanagement.sqlite_database.entities.Category;

import java.util.List;

public class ItemSelectCategoryTransactionAdapter extends RecyclerView.Adapter<ItemSelectCategoryTransactionAdapter.ItemSelectBankTransactionViewHolder> {

    private List<Category> categoryList;
    private OnCategorySelectedListener listener;

    // Constructor
    public ItemSelectCategoryTransactionAdapter(List<Category> categoryList, OnCategorySelectedListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemSelectBankTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_transaction, parent, false);
        return new ItemSelectBankTransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSelectBankTransactionViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());
        holder.tvDescriptionCategory.setText(category.getDescription());

        holder.itemView.setOnClickListener(v -> {
            // Khi người dùng chọn ngân hàng, gọi callback và truyền ngân hàng đã chọn
            listener.onCategorySelected(category);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ItemSelectBankTransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName , tvDescriptionCategory;

        public ItemSelectBankTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryNameTransaction);
            tvDescriptionCategory = itemView.findViewById(R.id.tvCategoryDescriptionTransaction);
        }
    }

    // Interface callback để khi chọn ngân hàng thì trả về thông tin ngân hàng đã chọn
    public interface OnCategorySelectedListener {
        void onCategorySelected(Category category);

    }
}
