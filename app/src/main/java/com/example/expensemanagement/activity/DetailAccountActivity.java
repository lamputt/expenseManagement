package com.example.expensemanagement.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ItemTransaction;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.sqlite_database.dao.BankDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Bank;
import com.example.expensemanagement.sqlite_database.entities.Transaction;
import com.example.expensemanagement.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailAccountActivity extends AppCompatActivity {
    private TextView tvBankName, tvAmount;
    private ImageView deleteAccountButton, backArrow;
    private RecyclerView recyclerViewTransactions;

    private BankDAO bankDAO;
    private TransactionDAO transactionDAO;
    private long bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_account); // Layout chi tiết ngân hàng

        // Ánh xạ các View
        tvBankName = findViewById(R.id.detailNameBank);
        tvAmount = findViewById(R.id.detailTotalAmount);
        deleteAccountButton = findViewById(R.id.delete_account);
        backArrow = findViewById(R.id.backArrowDetailAccount);
        recyclerViewTransactions = findViewById(R.id.recyclerView_List_Transaction_Detail_Account);

        // Khởi tạo DAO
        bankDAO = new BankDAO(this);
        transactionDAO = new TransactionDAO(this);

        // Lấy ID ngân hàng từ Intent
        bankId = getIntent().getLongExtra("bank_id", -1);

        // Hiển thị thông tin ngân hàng
        displayBankDetails();

        // Hiển thị danh sách giao dịch của ngân hàng
        displayTransactions();

        // Xử lý sự kiện nút "Xóa ngân hàng"
        deleteAccountButton.setOnClickListener(v -> {
            bankDAO.deleteBank(bankId);
            ToastUtil.showCustomToast(DetailAccountActivity.this, "Account Deleted", R.drawable.success_toast);
            finish(); // Quay lại màn hình trước
        });

        // Xử lý sự kiện nút "Back"
        backArrow.setOnClickListener(v -> finish());
    }

    private void displayBankDetails() {
        // Lấy thông tin ngân hàng từ DB
        Bank bank = bankDAO.getBankById(bankId);
        if (bank != null) {
            tvBankName.setText(bank.getName());
            DecimalFormat formatter = new DecimalFormat("###,###");
            String formattedAmount = formatter.format(bank.getAmount());
            tvAmount.setText(formattedAmount);
        }
    }

    private void displayTransactions() {
        // Lấy danh sách giao dịch của ngân hàng
        List<Transaction> transactionList = transactionDAO.getTransactionsByBankId(bankId);

        // Gắn Adapter cho RecyclerView
        ItemAdapterTransaction adapter = new ItemAdapterTransaction(transactionList);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTransactions.setAdapter(adapter);
    }

}
