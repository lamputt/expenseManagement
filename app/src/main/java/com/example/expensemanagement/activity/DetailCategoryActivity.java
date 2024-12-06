package com.example.expensemanagement.activity;

import android.os.Bundle;
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

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.sqlite_database.dao.CategoryDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Category;
import com.example.expensemanagement.sqlite_database.entities.Transaction;
import com.example.expensemanagement.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.List;

public class DetailCategoryActivity extends AppCompatActivity {
    private TextView tvNameCategory , tvAmount;
    private ImageView deleteCategoryButton , backArrow;
    private CategoryDAO categoryDAO;
    private TransactionDAO transactionDAO;
    private long categoryId;
    private RecyclerView recyclerViewTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_category_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvNameCategory = findViewById(R.id.detailNameCategory);
        tvAmount = findViewById(R.id.detailTotalSpentCategory);
        deleteCategoryButton = findViewById(R.id.delete_category);
        backArrow = findViewById(R.id.backArrowDetailCategory);
        recyclerViewTransaction = findViewById(R.id.recyclerView_List_Transaction_Detail_Category);
        categoryDAO = new CategoryDAO(this);
        transactionDAO = new TransactionDAO(this);

        // Lấy ID ngân hàng từ Intent
        categoryId = getIntent().getLongExtra("category_id", -1);

        displayTransactions();

        Category category = categoryDAO.getCategoryByIdTypeExpense(categoryId);
        if (category != null) {
            tvNameCategory.setText(category.getName());
            DecimalFormat formatter = new DecimalFormat("###,###");
            String formattedAmount = formatter.format(category.getTotalSpent());  // Không cần thêm "đ" vì đã có sẵn trong layout
            tvAmount.setText(formattedAmount);
        }


        deleteCategoryButton.setOnClickListener(v -> {
            categoryDAO.deleteCategory(categoryId);
            ToastUtil.showCustomToast(DetailCategoryActivity.this, "Category Deleted", R.drawable.success_toast);
            finish();  // Quay lại màn hình trước sau khi xóa
        });

        backArrow.setOnClickListener(v -> {
            finish();
        });
    }
    private void displayTransactions() {
        // Lấy danh sách giao dịch của ngân hàng
        List<Transaction> transactionList = transactionDAO.getTransactionsByCategoryId(categoryId);

        // Gắn Adapter cho RecyclerView
        ItemAdapterTransaction adapter = new ItemAdapterTransaction(transactionList);
        recyclerViewTransaction.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTransaction.setAdapter(adapter);
    }
}