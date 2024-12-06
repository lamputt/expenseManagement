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

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaction, container, false);

        LinearLayout lnSeeFinancial = view.findViewById(R.id.LnSeeFinancial);

        loadTransactions();

        // Thiết lập click listener
        lnSeeFinancial.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity
            Intent intent = new Intent(getActivity(), FinancialReportActivity.class);
            startActivity(intent);
        });

        return view;
    }

    //tạo hàm load lại dữ liệu khi quay lại trang
    @Override
    public void onResume() {
        super.onResume();
        loadTransactions();
    }

    private void loadTransactions() {
        TransactionDAO transactionDAO = new TransactionDAO(requireContext());

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Transaction> transactionList = transactionDAO.getAllTransactions();

        // Gắn Adapter
        ItemAdapterTransaction adapter = new ItemAdapterTransaction(transactionList);
        recyclerView.setAdapter(adapter);
    }
}