package com.example.expensemanagement.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.FinancialReportActivity;


public class TransactionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

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