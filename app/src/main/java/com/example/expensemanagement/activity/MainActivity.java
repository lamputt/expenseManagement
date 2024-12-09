package com.example.expensemanagement.activity;

import com.example.expensemanagement.Model.SharedViewModel;
import com.example.expensemanagement.databinding.ActivityMainBinding;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensemanagement.R;
import com.example.expensemanagement.fragments.BudgetFragment;
import com.example.expensemanagement.fragments.HomeFragment;
import com.example.expensemanagement.fragments.ProfileFragment;
import com.example.expensemanagement.fragments.TransactionFragment;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Budget;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    ActivityMainBinding binding;
    private SharedViewModel sharedViewModel;
    private BudgetDAO budgetDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        budgetDAO = new BudgetDAO(this);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homepage) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.budgetpage) {
                replaceFragment(new BudgetFragment());
            } else if (itemId == R.id.profilepage) {
                replaceFragment(new ProfileFragment());
            } else if (itemId == R.id.transactionpage) {
                replaceFragment(new TransactionFragment());
            }
            return true;
        });
        bottomAppBar = findViewById(R.id.bottomAppBar);
        // Khởi tạo ViewModel
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        // Cập nhật trạng thái redDot ngay khi khởi chạy
        boolean hasNewNotifications = checkInitialNotifications();
        sharedViewModel.updateRedDotStatus(hasNewNotifications);
        replaceFragment(new HomeFragment());
        binding.floatingActionButton.setOnClickListener(v -> Showdialog());
    }

    public SharedViewModel getSharedViewModel() {
        return sharedViewModel;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout , fragment);
        fragmentTransaction.commit();
    }

    // Phương thức để ẩn BottomAppBar
    public void hideBottomAppBar() {
        bottomAppBar.setVisibility(View.GONE); // Ẩn BottomAppBar
    }

    // Phương thức để hiển thị BottomAppBar
    public void showBottomAppBar() {
        bottomAppBar.setVisibility(View.VISIBLE); // Hiển thị BottomAppBar
    }

    // Phương thức để ẩn FloatingActionButton
    public void hideFloatingActionButton() {
        binding.floatingActionButton.setVisibility(View.GONE);
    }

    // Phương thức để hiển thị FloatingActionButton
    public void showFloatingActionButton() {
        binding.floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void Showdialog () {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomshett_layout);

        LinearLayout expenseLayout = dialog.findViewById(R.id.Lnexpense);
        LinearLayout incomeLayout = dialog.findViewById(R.id.Lnincome);

        expenseLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
            startActivity(intent);
        });

        incomeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
            startActivity(intent);
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onResume() {
        super.onResume();
        showBottomAppBar(); // Gọi trực tiếp phương thức trong MainActivity
        showFloatingActionButton(); // Hiển thị lại FloatingActionButton khi quay lại
    }

    private boolean checkInitialNotifications() {
        // Lấy danh sách ngân sách từ database và kiểm tra điều kiện remaining <= 0
        BudgetDAO budgetDAO = new BudgetDAO(this);
        TransactionDAO transactionDAO = new TransactionDAO(this);
        List<Budget> allBudgets = budgetDAO.getAllBudgets();
        for (Budget budget : allBudgets) {
            double sumExpense = transactionDAO.getTotalExpenseByCategory(
                    budget.getCategoryId(),
                    budget.getDateStart(),
                    budget.getDateEnd()
            );
            if (budget.getAmount() - sumExpense <= 0) {
                return true;
            }
        }
        return false;
    }


}