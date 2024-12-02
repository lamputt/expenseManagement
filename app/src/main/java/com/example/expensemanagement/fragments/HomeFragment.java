package com.example.expensemanagement.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.Model.ItemTransaction;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.util.List;

public class HomeFragment extends Fragment {

    private TransactionDAO transactionDAO;
    private Button seeAll;
    private RecyclerView recyclerView;
    private ItemAdapterTransaction adapter;
    private TextView totalIncomeTextView, totalExpenseTextView, tvSumAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gắn layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        transactionDAO = new TransactionDAO(requireContext());

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Khởi tạo Adapter với danh sách ban đầu
        List<Transaction> transactionList = transactionDAO.getAllTransactions();
        adapter = new ItemAdapterTransaction(transactionList);
        recyclerView.setAdapter(adapter);

        // Liên kết các TextView
        totalIncomeTextView = view.findViewById(R.id.total_income);
        totalExpenseTextView = view.findViewById(R.id.total_expense);
        tvSumAmount = view.findViewById(R.id.tvSumAmount);

        // Tính toán và cập nhật tổng tiền
        updateTotals(transactionList);

        // Xử lý nút seeAll
        seeAll = view.findViewById(R.id.btnSeeAll);
        seeAll.setOnClickListener(v -> {
            // Chuyển sang TransactionFragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentHome, new TransactionFragment());
            transaction.addToBackStack(null); // Thêm vào BackStack để quay lại
            transaction.commit();
        });

        return view;
    }

    // Load lại dữ liệu của trang khi quay lại
    @Override
    public void onResume() {
        super.onResume();
        // Lấy lại danh sách giao dịch mới
        List<Transaction> transactionList = transactionDAO.getAllTransactions();
        adapter.updateData(transactionList); // Cập nhật lại Adapter
        updateTotals(transactionList); // Cập nhật tổng tiền
    }

    // Phương thức cập nhật tổng thu nhập, chi tiêu và số dư
    private void updateTotals(List<Transaction> transactionList) {
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction transaction : transactionList) {
            if ("income".equals(transaction.getType())) { // So sánh đúng kiểu String
                totalIncome += transaction.getAmount();
            } else {
                totalExpense += transaction.getAmount();
            }
        }
        totalIncomeTextView.setText(String.valueOf(totalIncome));
        totalExpenseTextView.setText(String.valueOf(totalExpense));
        tvSumAmount.setText(String.valueOf(totalIncome - totalExpense));
    }
}
