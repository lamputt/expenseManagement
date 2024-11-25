package com.example.expensemanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.expensemanagement.fragments.ExpenseTabFragment;
import com.example.expensemanagement.fragments.IncomeTabFragment;
import com.example.expensemanagement.R;
import com.example.expensemanagement.fragments.TotalExpenseFragment;
import com.example.expensemanagement.fragments.TotalIncomeFragment;
import com.example.expensemanagement.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FinancialReportActivity extends AppCompatActivity {

    private ViewPager2 viewPagerTop;
    private ViewPager2 viewPagerBottom;
    private TabLayout tabLayout;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_financial_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    
        Spinner spinner = findViewById(R.id.spinner);

        // Tạo adapter cho Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        back = findViewById(R.id.back_financialReport);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Bắt sự kiện chọn
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                Toast.makeText(FinancialReportActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPagerFinancialReport);

        // Setup ViewPager Adapter
        viewPagerTop = findViewById(R.id.viewPagerTotalIncomeAndExpense);
        viewPagerBottom = findViewById(R.id.viewPagerFinancialReport);

        // Khởi tạo TabLayout
        tabLayout = findViewById(R.id.tabLayout);

        // Khởi tạo adapter cho ViewPager2
        ViewPagerAdapter viewPagerAdapterTop = new ViewPagerAdapter(this);
        viewPagerAdapterTop.addFragment(new TotalExpenseFragment());
        viewPagerAdapterTop.addFragment(new TotalIncomeFragment());
        viewPagerTop.setAdapter(viewPagerAdapterTop);

        ViewPagerAdapter viewPagerAdapterBottom = new ViewPagerAdapter(this);
        viewPagerAdapterBottom.addFragment(new ExpenseTabFragment());
        viewPagerAdapterBottom.addFragment(new IncomeTabFragment());
        viewPagerBottom.setAdapter(viewPagerAdapterBottom);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPagerBottom, (tab, position) -> {
            if (position == 0) {
                tab.setText("Expense");
            } else if (position == 1) {
                tab.setText("Income");
            }
        }).attach();

        // Thiết lập đồng bộ giữa hai ViewPager2
        viewPagerBottom.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPagerTop.setCurrentItem(position); // Đồng bộ ViewPager trên
            }
        });

        viewPagerTop.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPagerBottom.setCurrentItem(position); // Đồng bộ ViewPager dưới
            }
        });
    }


}