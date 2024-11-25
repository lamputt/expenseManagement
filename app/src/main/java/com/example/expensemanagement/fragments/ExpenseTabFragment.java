package com.example.expensemanagement.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ItemDateTransaction;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemDateTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Tạo view của fragment
        View view = inflater.inflate(R.layout.fragment_expense_tab, container, false);

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewExpenseTab);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Tạo danh sách các item
        List<ItemDateTransaction> itemList = new ArrayList<>();
        itemList.add(new ItemDateTransaction("January", "100$"));
        itemList.add(new ItemDateTransaction("February", "200$"));
        itemList.add(new ItemDateTransaction("March", "300$"));
        itemList.add(new ItemDateTransaction("May", "400$"));
        itemList.add(new ItemDateTransaction("March", "300$"));
        itemList.add(new ItemDateTransaction("March", "300$"));
        itemList.add(new ItemDateTransaction("March", "300$"));
        itemList.add(new ItemDateTransaction("March", "300$"));
        itemList.add(new ItemDateTransaction("March", "300$"));

        // Gắn Adapter vào RecyclerView
        ItemDateTransactionAdapter adapter = new ItemDateTransactionAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Trả về view sau khi đã thiết lập đầy đủ
        return view;
    }
}
