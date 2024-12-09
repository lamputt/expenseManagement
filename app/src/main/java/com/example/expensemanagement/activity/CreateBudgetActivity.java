package com.example.expensemanagement.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemSelectCategoryTransactionAdapter;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.dao.CategoryDAO;
import com.example.expensemanagement.sqlite_database.entities.Category;
import com.example.expensemanagement.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CreateBudgetActivity extends AppCompatActivity {
    private Button Continue;
    private ImageView back;
    private EditText etAmount , selectCategory;
    private long selectedCategoryId = -1 ;
    private String totalAmount;
    private Double amount;
    private BudgetDAO budgetDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CreateBudget), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectCategory = findViewById(R.id.edtSelectCategoryBudget);
        Continue = findViewById(R.id.btnContinueCreateBudget);
        back = findViewById(R.id.backArrowCreateBudget);
        budgetDAO = new BudgetDAO(this);

        back.setOnClickListener(v -> finish());

        selectCategory.setOnClickListener(v -> ShowdialogCategory());
        etAmount = findViewById(R.id.et_amountBudget);

        etAmount.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[^0-9]", "");

                    if (cleanString.length() > 9) {
                        ToastUtil.showCustomToast(CreateBudgetActivity.this, "Do not enter more than 9 digits", R.drawable.warning_toast);
                        cleanString = cleanString.substring(0, 9);
                    }

                    long parsed = cleanString.isEmpty() ? 0 : Long.parseLong(cleanString);

                    DecimalFormat formatter = new DecimalFormat("###,###");
                    String formatted = formatter.format(parsed);

                    current = formatted;
                    etAmount.setText(formatted);
                    etAmount.setSelection(formatted.length());
                    etAmount.addTextChangedListener(this);
                }
            }
        });

        Continue.setOnClickListener(v -> {
            totalAmount = etAmount.getText().toString().trim();
            totalAmount = totalAmount.replace(",", "");
            amount = Double.parseDouble(totalAmount);

            if (totalAmount.isEmpty() || selectedCategoryId == -1) {
                ToastUtil.showCustomToast(CreateBudgetActivity.this, "Please fill in all information", R.drawable.warning_toast);
                return;
            } else {
                if (budgetDAO.isCategoryExist(selectedCategoryId)) {
                    ToastUtil.showCustomToast(CreateBudgetActivity.this, "This category has already been added", R.drawable.warning_toast);
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                String startDate = new SimpleDateFormat("yyy-MM-dd").format(calendar.getTime());

                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                String endDate = new SimpleDateFormat("yyy-MM-dd").format(calendar.getTime());

                long result = budgetDAO.addBudget(selectedCategoryId, amount, startDate, endDate);
                if (result != -1) {
                    ToastUtil.showCustomToast(CreateBudgetActivity.this, "Add Transaction successfully", R.drawable.success_toast);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);  // Trả kết quả về BudgetFragment
                    finish(); // Quay lại màn hình BudgetFragment
                } else {
                    ToastUtil.showCustomToast(CreateBudgetActivity.this, "Failed to save data!", R.drawable.warning_toast);
                }
            }
        });
    }

    private void ShowdialogCategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_select_category_transaction);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewListCategorySelectTransaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryDAO categoryDAO = new CategoryDAO(this);
        List<Category> categoryList = categoryDAO.getAllCategories();

        ItemSelectCategoryTransactionAdapter adapter = new ItemSelectCategoryTransactionAdapter(categoryList, category -> {
            selectCategory.setText(category.getName());
            selectedCategoryId = category.getId();
            dialog.dismiss();
        });

        recyclerView.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
