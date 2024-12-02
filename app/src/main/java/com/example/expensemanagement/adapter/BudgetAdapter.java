package com.example.expensemanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.DetailBudgetActivity;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.text.DecimalFormat;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private final List<Budget> budgetList;
    private final TransactionDAO transactionDAO;
    private final Context context;

    public BudgetAdapter(List<Budget> budgetList, TransactionDAO transactionDAO, Context context) {
        this.budgetList = budgetList;
        this.transactionDAO = transactionDAO;
        this.context = context;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgetList.get(position);

        // Định dạng số tiền
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedTotalBudget = decimalFormat.format(budget.getAmount());

        // Tính tổng chi tiêu sử dụng phương thức getTotalExpenseByCategory
        double sumExpense = transactionDAO.getTotalExpenseByCategory(
                budget.getCategoryId(),
                budget.getDateStart(),
                budget.getDateEnd()
        );

        String formattedExpense = decimalFormat.format(sumExpense);

        // Tính số tiền còn lại
        double remaining = budget.getAmount() - sumExpense;
        String formattedRemaining = decimalFormat.format(remaining);
        BudgetDAO budgetDAO = new BudgetDAO(context);
        String categoryName = budgetDAO.getCategoryNameById(budget.getCategoryId());
        // Gán dữ liệu
        holder.tvCategoryName.setText(categoryName); // Có thể thay bằng tên danh mục
        holder.tvTotalBudget.setText(formattedTotalBudget );
        holder.tvSumExpense.setText(formattedExpense );
        holder.tvSumRemaining.setText(formattedRemaining );

        // Hiển thị cảnh báo nếu vượt ngân sách
        if (remaining < 0) {
            holder.tvExceedWarning.setVisibility(View.VISIBLE);
        } else {
            holder.tvExceedWarning.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailBudgetActivity.class);
            intent.putExtra("budgetId", budget.getId());
            intent.putExtra("categoryName", categoryName);
            intent.putExtra("remaining", remaining);
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvTotalBudget, tvSumExpense, tvSumRemaining, tvExceedWarning;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_Namecategory);
            tvTotalBudget = itemView.findViewById(R.id.totalBudget);
            tvSumExpense = itemView.findViewById(R.id.tv_sumExpensebyCategory);
            tvSumRemaining = itemView.findViewById(R.id.tv_sumRemaining);
            tvExceedWarning = itemView.findViewById(R.id.tv_exceed_warning);
        }
    }
}
