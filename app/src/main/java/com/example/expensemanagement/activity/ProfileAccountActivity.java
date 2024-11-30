package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.BankAccount;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.BankAdapter;
import com.example.expensemanagement.adapter.CategoryAdapter;
import com.example.expensemanagement.sqlite_database.dao.BankDAO;
import com.example.expensemanagement.sqlite_database.entities.Bank;
import com.example.expensemanagement.sqlite_database.entities.Category;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProfileAccountActivity extends AppCompatActivity {
    private Button btnAddNewWallet;
    private ImageView back;
    private RecyclerView recyclerView;
    private BankDAO bankDAO;
    private TextView tvSumAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);

        tvSumAmount = findViewById(R.id.tvTotalSpentBankAccount);
        back = findViewById(R.id.backArrowAccount);
        bankDAO = new BankDAO(this);
        back.setOnClickListener(v -> finish());


        btnAddNewWallet = findViewById(R.id.btnAddNewWallet);
        btnAddNewWallet.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileAccountActivity.this, AddNewWalletActivity.class);
            startActivity(intent); // Mã request là 100
        });

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recycleListBank);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBank();

    }

    private void loadBank() {
        List<Bank> bankList = bankDAO.getAllBanks();
        if (bankList == null) {
            bankList = new ArrayList<>();
            tvSumAmount.setText("0");
        }
        BankAdapter adapter = new BankAdapter(bankList);
        recyclerView.setAdapter(adapter);
        double totalSpent = 0;
        for (Bank bank : bankList) {
            totalSpent += bank.getAmount();
        }
        // Định dạng số tiền với dấu chấm phân cách
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedAmount = formatter.format(totalSpent);
        tvSumAmount.setText(formattedAmount);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadBank(); // Tải lại danh sách khi quay lại Activity
    }

}
