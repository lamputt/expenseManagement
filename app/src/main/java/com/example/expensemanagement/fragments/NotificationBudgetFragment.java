package com.example.expensemanagement.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.expensemanagement.Model.SharedViewModel;
import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.MainActivity;
import com.example.expensemanagement.adapter.NotificationAdapter;
import com.example.expensemanagement.sqlite_database.dao.BudgetDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.util.ArrayList;
import java.util.List;


public class NotificationBudgetFragment extends Fragment {
    private ImageView back;
    private RecyclerView recyclerViewNotification;
    private List<Budget> budgetsWithExceed;
    private BudgetDAO budgetDAO;
    private NotificationAdapter notificationAdapter;
    private TransactionDAO transactionDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_notification_budget, container, false);
        budgetDAO = new BudgetDAO(requireContext());
        transactionDAO = new TransactionDAO(requireContext());
        recyclerViewNotification = rootView.findViewById(R.id.recyclerViewNotification);
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(requireContext()));
        back = rootView.findViewById(R.id.ImageViewBackNotification);

        back.setOnClickListener(v -> getActivity().onBackPressed());
        List<Budget> allBudgets = budgetDAO.getAllBudgets();
        budgetsWithExceed = new ArrayList<>();
        for (Budget budget : allBudgets) {
            double sumExpense = transactionDAO.getTotalExpenseByCategory(budget.getCategoryId(), budget.getDateStart(), budget.getDateEnd());
            double remaining = budget.getAmount() - sumExpense;
            if (remaining <= 0) {
                budgetsWithExceed.add(budget);  // Thêm vào danh sách các ngân sách vượt quá
            }
        }
        // Gán Adapter cho RecyclerView
        notificationAdapter = new NotificationAdapter(budgetsWithExceed);
        recyclerViewNotification.setAdapter(notificationAdapter);

        if (budgetsWithExceed.isEmpty()) {
            recyclerViewNotification.setVisibility(View.GONE);
            rootView.findViewById(R.id.no_notification).setVisibility(View.VISIBLE);
            // Không có thông báo, ẩn redDot
        } else {
            recyclerViewNotification.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.no_notification).setVisibility(View.GONE);
            // Có thông báo, hiển thị redDot
        }
        // Cập nhật trạng thái redDot
        MainActivity mainActivity = (MainActivity) requireActivity();
        SharedViewModel sharedViewModel = mainActivity.getSharedViewModel();
        boolean hasNewNotification = budgetsWithExceed != null && !budgetsWithExceed.isEmpty();
        sharedViewModel.updateRedDotStatus(hasNewNotification);
        sharedViewModel.markNotificationsAsSeen(); // Ẩn redDot
        return rootView;

    }
    @Override
    public void onStop() {
        super.onStop();
        // Hiển thị lại BottomAppBar khi Fragment này không còn hiển thị
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showBottomAppBar();
            mainActivity.showFloatingActionButton();
        }
    }
}