package com.example.expensemanagement.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.DetailAccountActivity;
import com.example.expensemanagement.activity.DetailCategoryActivity;
import com.example.expensemanagement.sqlite_database.entities.Category;

import java.text.DecimalFormat;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedTotalSpent = formatter.format(category.getTotalSpent());
        holder.tvTotalSpent.setText(formattedTotalSpent);
        holder.tvCategoryName.setText(category.getName());
        holder.tvCategoryDescription.setText(category.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailCategoryActivity.class);
            intent.putExtra("category_id", category.getId());  // Truyền ID của ngân hàng
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvCategoryDescription , tvTotalSpent;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvTotalSpent = itemView.findViewById(R.id.tvTotalSpent);
            tvCategoryDescription = itemView.findViewById(R.id.tvCategoryDescription);
        }
    }
}
