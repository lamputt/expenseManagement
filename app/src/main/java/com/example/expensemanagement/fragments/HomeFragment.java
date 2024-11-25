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

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;
import com.example.expensemanagement.Model.ItemTransaction;
import com.example.expensemanagement.adapter.ItemAdapterTransaction;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gắn layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Tạo danh sách dữ liệu
        List<ItemTransaction> itemList = new ArrayList<>();
        itemList.add(new ItemTransaction("Shopping", "Buy some grocery", "$120", "10:00AM"));
        itemList.add(new ItemTransaction("Work", "Prepare presentation", "$0", "2:00PM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        itemList.add(new ItemTransaction("Exercise", "Morning run", "$0", "6:30AM"));
        // Thêm nhiều item khác nếu cần

        // Gắn Adapter
        ItemAdapterTransaction adapter = new ItemAdapterTransaction(itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
