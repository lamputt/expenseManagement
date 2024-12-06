package com.example.expensemanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.utils.ToastUtil;

import java.text.DecimalFormat;

public class DetailBudgetActivity extends AppCompatActivity {
    private TextView tvNameCategory, tvRemaining;
    private ImageView imgDelete ,back;

    private long budgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_budget);

        // Ánh xạ view
        back = findViewById(R.id.backDetailBudget);
        tvNameCategory = findViewById(R.id.nameCategoryDetailBudget);
        tvRemaining = findViewById(R.id.ReamaningDetailBudget);
        imgDelete = findViewById(R.id.delete_budget);
        back.setOnClickListener(v -> finish());

        // Lấy dữ liệu từ intent
        budgetId = getIntent().getLongExtra("budgetId", -1);
        String categoryName = getIntent().getStringExtra("categoryName");
        double remaining = getIntent().getDoubleExtra("remaining", 0.0);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedRemaining = decimalFormat.format(remaining);

        // Hiển thị dữ liệu
        tvNameCategory.setText(categoryName);
        tvRemaining.setText(formattedRemaining);

        // Xử lý xóa ngân sách
        imgDelete.setOnClickListener(v -> deleteBudget());
    }

    private void deleteBudget() {
        if (budgetId != -1) {
            BudgetDAO budgetDAO = new BudgetDAO(this);
            budgetDAO.deleteBudget(budgetId);
            ToastUtil.showCustomToast(DetailBudgetActivity.this, "Budget deleted successfully!", R.drawable.success_toast);
            // Gửi kết quả về BudgetFragment
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to delete budget.", Toast.LENGTH_SHORT).show();
        }
    }
}
