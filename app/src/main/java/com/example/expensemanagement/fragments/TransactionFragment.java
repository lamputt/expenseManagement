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

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.FinancialReportActivity;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.util.List;


public class TransactionFragment extends Fragment {
    private TransactionDAO transactionDAO;
    private RecyclerView recyclerView;
    private ItemAdapterTransaction adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        // Tìm RecyclerView theo ID
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo TransactionDAO và lấy danh sách giao dịch
        transactionDAO = new TransactionDAO(getActivity());
        List<Transaction> transactionList = transactionDAO.getAllTransactions();

        // Cập nhật Adapter
        adapter = new ItemAdapterTransaction(transactionList);
        recyclerView.setAdapter(adapter);

        // Tìm LinearLayout theo ID
        LinearLayout lnSeeFinancial = view.findViewById(R.id.LnSeeFinancial);

        // Thiết lập click listener
        lnSeeFinancial.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity
            Intent intent = new Intent(getActivity(), FinancialReportActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
