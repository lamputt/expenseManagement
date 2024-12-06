package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.CategoryDAO;
import com.example.expensemanagement.utils.ToastUtil;

public class AddNewCategoryActivity extends AppCompatActivity {
    private CategoryDAO categoryDAO;
    private Button Continue;
    private EditText edtCategory;
    private EditText edtDescription;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AddNewCategoryActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        categoryDAO = new CategoryDAO(this);
        edtCategory = findViewById(R.id.edtCategory);
        edtDescription = findViewById(R.id.edtDescription);
        Continue = findViewById(R.id.btnContinueAddNewCategory);
        Back = findViewById(R.id.backArrowAddNewCategories);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCategory = edtCategory.getText().toString().trim();
                String descriptionCategory = edtDescription.getText().toString().trim();

                if (nameCategory.isEmpty() || descriptionCategory.isEmpty()) {
                    ToastUtil.showCustomToast(AddNewCategoryActivity.this, "Please fill all fields", R.drawable.warning_toast);
                } else {
                    // Perform sign up logic here (e.g., API call)
                    ToastUtil.showCustomToast(AddNewCategoryActivity.this, "Add category success", R.drawable.success_toast);
                    finish();
                }
                long result = categoryDAO.addCategory(nameCategory, descriptionCategory);
                if (result != -1) {
                    Intent intent = new Intent(AddNewCategoryActivity.this, ProfileCategoriesActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showCustomToast(AddNewCategoryActivity.this, "Failed to save data!", R.drawable.warning_toast);
                }

            }
        });

    }
}