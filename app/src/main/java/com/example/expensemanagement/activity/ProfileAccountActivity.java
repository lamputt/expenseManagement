package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.BankAccount;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.BankAccountAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfileAccountActivity extends AppCompatActivity {
    private Button btnAddNewWallet;
    private ImageView back;
    private RecyclerView recyclerView;
    private BankAccountAdapter adapter;
    private ArrayList<BankAccount> bankAccounts;
    private TextView tvSumAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);

        tvSumAmount = findViewById(R.id.tvSumAmount);
        back = findViewById(R.id.backArrowAccount);

        back.setOnClickListener(v -> finish());

        // Khởi tạo bankAccounts trước khi sử dụng
        bankAccounts = new ArrayList<>();

        btnAddNewWallet = findViewById(R.id.btnAddNewWallet);
        btnAddNewWallet.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileAccountActivity.this, AddNewWalletActivity.class);
            startActivityForResult(intent, 100); // Mã request là 100
        });

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recycleListBank);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo và thiết lập Adapter
        adapter = new BankAccountAdapter(bankAccounts);
        recyclerView.setAdapter(adapter);

        // Cập nhật tổng số tiền
        updateTotalAmount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String bankName = data.getStringExtra("bankName");
            String accountNumber = data.getStringExtra("accountNumber");
            long amount = Long.parseLong(data.getStringExtra("amount")); // Số tiền đã được gửi mà không có dấu chấm

            // Thêm dữ liệu mới vào danh sách
            BankAccount newBankAccount = new BankAccount(bankName, accountNumber, amount);
            bankAccounts.add(newBankAccount);

            // Cập nhật RecyclerView
            adapter.notifyItemInserted(bankAccounts.size() - 1);

            updateTotalAmount();
        }
    }

    private void updateTotalAmount() {
        long totalAmount = 0;
        for (BankAccount account : bankAccounts) {
            totalAmount += account.getMoney();
        }

        // Định dạng số tiền với dấu chấm phân cách
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedAmount = formatter.format(totalAmount);
        tvSumAmount.setText(formattedAmount); // Cập nhật TextView với số tiền đã định dạng
    }
}
