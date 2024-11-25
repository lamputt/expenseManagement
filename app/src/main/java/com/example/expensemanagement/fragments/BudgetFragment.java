package com.example.expensemanagement.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.CreateBudgetActivity;

import java.util.Arrays;
import java.util.List;

public class BudgetFragment extends Fragment {

    private Button btnCreate;
    private Spinner spinnerBudget;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        // Khởi tạo nút btnCreate
        btnCreate = view.findViewById(R.id.btnCreateBudget);

        // Xử lý sự kiện nhấn nút
        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CreateBudgetActivity.class);
            startActivity(intent);
        });

        // Khởi tạo Spinner
        spinnerBudget = view.findViewById(R.id.spinnerBudget);

        // Danh sách 12 tháng bằng tiếng Anh
        List<String> months = Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Gắn dữ liệu vào Spinner bằng ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                months
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBudget.setAdapter(adapter);

        // Thêm OnItemSelectedListener vào Spinner
        spinnerBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = parent.getItemAtPosition(position).toString();
                // Hiển thị tháng được chọn (hoặc xử lý logic khác)
                Toast.makeText(requireContext(), "Selected: " + selectedMonth, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì nếu không chọn
            }
        });

        return view;
    }
}
