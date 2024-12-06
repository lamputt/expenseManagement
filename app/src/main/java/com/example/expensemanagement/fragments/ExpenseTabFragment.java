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
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;

import java.util.List;

public class ExpenseTabFragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionDAO transactionDAO; // Khai báo TransactionDAO

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_tab, container, false);

        // Khởi tạo đối tượng transactionDAO
        transactionDAO = new TransactionDAO(requireContext());

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewExpenseTab);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Lấy dữ liệu từ cơ sở dữ liệu
        updateData(false);

        // Trả về view sau khi đã thiết lập đầy đủ
        return view;
    }

    public void updateData(boolean isYear) {
        // Kiểm tra xem transactionDAO có null hay không trước khi gọi phương thức
        if (transactionDAO != null) {
            // Lấy dữ liệu từ database dựa trên isYear
            List<ItemDateTransaction> data = transactionDAO.getDataByDate("expense", isYear);

            // Gắn dữ liệu vào adapter
            ItemDateTransactionAdapter adapter = new ItemDateTransactionAdapter(data);
            recyclerView.setAdapter(adapter);
        } else {
            // In ra log nếu transactionDAO là null
            System.out.println("TransactionDAO is null!");
        }
    }
}
