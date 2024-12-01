package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.BankAccount;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.CategoryAdapter;
import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.dao.CategoryDAO;
import com.example.expensemanagement.sqlite_database.entities.Category;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProfileCategoriesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryDAO categoryDAO;
    private Button btnAddNewCategory;
    private ImageView back;
    private TextView tvTotalSpent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_categories);
        recyclerView = findViewById(R.id.recycleListCategory);
        tvTotalSpent = findViewById(R.id.tvSumAmountCategories);
        btnAddNewCategory = findViewById(R.id.btnAddNewCategory);
        categoryDAO = new CategoryDAO(this);
        back = findViewById(R.id.backArrowCategories);
        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCategories();

        // Xử lý sự kiện khi nhấn nút "Add New Category"
        btnAddNewCategory.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileCategoriesActivity.this, AddNewCategoryActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void loadCategories() {
        List<Category> categoryList = categoryDAO.getAllCategoriesByExpense();
        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        long totalSpent = 0;
        for (Category category : categoryList) {
            totalSpent += category.getTotalSpent();
        }
        // Định dạng số tiền với dấu chấm phân cách
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedAmount = formatter.format(totalSpent);
        tvTotalSpent.setText(formattedAmount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories(); // Tải lại danh sách khi quay lại Activity
    }
}