package com.example.expensemanagement.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.expensemanagement.Model.ItemTransaction;
import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.FinancialReportActivity;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.util.ArrayList;
import java.util.List;


public class TransactionFragment extends Fragment {

    private TransactionDAO transactionDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        transactionDAO = new TransactionDAO(requireContext());

        // Tìm LinearLayout theo ID
        LinearLayout lnSeeFinancial = view.findViewById(R.id.LnSeeFinancial);

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Transaction> transactionList = transactionDAO.getAllTransactions();

        // Tạo danh sách dữ liệu
        List<ItemTransaction> itemList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            itemList.add(new ItemTransaction(transaction.getCategory().getName(),
                    transaction.getType(),
                    transaction.getDescription(),
                    String.valueOf(transaction.getAmount()),
                    transaction.getDate()));
        }
        // Thêm nhiều item khác nếu cần

        // Gắn Adapter
        ItemAdapterTransaction adapter = new ItemAdapterTransaction(itemList);
        recyclerView.setAdapter(adapter);

        // Thiết lập click listener
        lnSeeFinancial.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity
            Intent intent = new Intent(getActivity(), FinancialReportActivity.class);
            startActivity(intent);
        });

        return view;
    }
}