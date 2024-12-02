package com.example.expensemanagement.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.CreateBudgetActivity;
import com.example.expensemanagement.adapter.BudgetAdapter;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BudgetFragment extends Fragment {

    private TextView tvNoData;
    private RecyclerView recyclerView;
    private Button btnCreate;
    private TextView tvDataBudget;
    private BudgetAdapter adapter;
    private BudgetDAO budgetDAO;
    private TransactionDAO transactionDAO;

    private final ActivityResultLauncher<Intent> createBudgetLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Khi quay lại từ CreateBudgetActivity hoặc DetailBudgetActivity, tải lại danh sách ngân sách
                    loadBudgets();
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        // Khởi tạo các đối tượng DAO
        budgetDAO = new BudgetDAO(requireContext());
        transactionDAO = new TransactionDAO(requireContext());

        // Xử lý các View
        btnCreate = view.findViewById(R.id.btnCreateBudget);
        tvNoData = view.findViewById(R.id.tv_no_data);
        recyclerView = view.findViewById(R.id.recycler_view);

        // Xử lý sự kiện nhấn nút tạo ngân sách
        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CreateBudgetActivity.class);
            createBudgetLauncher.launch(intent);
        });

        // Hiển thị ngày hiện tại trong format "MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        tvDataBudget = view.findViewById(R.id.tvDateBudget);
        tvDataBudget.setText(currentDate);

        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Tải danh sách ngân sách
        loadBudgets();

        return view;
    }

    // Hàm để tải và hiển thị danh sách ngân sách
    private void loadBudgets() {
        List<Budget> budgetList = budgetDAO.getAllBudgets();

        if (budgetList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);

            // Tạo và gán adapter cho RecyclerView
            adapter = new BudgetAdapter(budgetList, transactionDAO, requireContext());
            recyclerView.setAdapter(adapter);
        }
    }
}