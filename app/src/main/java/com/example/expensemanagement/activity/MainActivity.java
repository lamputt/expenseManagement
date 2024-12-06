package com.example.expensemanagement.activity;

import com.example.expensemanagement.databinding.ActivityMainBinding;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expensemanagement.R;
import com.example.expensemanagement.fragments.BudgetFragment;
import com.example.expensemanagement.fragments.HomeFragment;
import com.example.expensemanagement.fragments.ProfileFragment;
import com.example.expensemanagement.fragments.TransactionFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
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

        binding.floatingActionButton.setOnClickListener(v -> Showdialog());
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout , fragment);
        fragmentTransaction.commit();
    }

    private void Showdialog () {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomshett_layout);

        LinearLayout expenseLayout = dialog.findViewById(R.id.Lnexpense);
        LinearLayout incomeLayout = dialog.findViewById(R.id.Lnincome);

        expenseLayout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this , "Expense click" , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
            startActivity(intent);
        });

        incomeLayout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this , "Income click" , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
            startActivity(intent);
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}