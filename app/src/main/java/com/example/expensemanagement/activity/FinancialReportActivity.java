package com.example.expensemanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FinancialReportActivity extends AppCompatActivity {

    private TransactionDAO transactionDAO;

    private ViewPager2 viewPagerTop;
    private ViewPager2 viewPagerBottom;
    private TabLayout tabLayout;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        transactionDAO = new TransactionDAO(this);
        setContentView(R.layout.activity_financial_report);

        // Khởi tạo các ViewPager2
        viewPagerTop = findViewById(R.id.viewPagerTotalIncomeAndExpense); // Đảm bảo viewPagerTop được khởi tạo
        viewPagerBottom = findViewById(R.id.viewPagerFinancialReport); // Đảm bảo viewPagerBottom được khởi tạo

        // Thiết lập padding cho view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tạo và gán adapter cho ViewPagerTop
        ViewPagerAdapter viewPagerAdapterTop = new ViewPagerAdapter(this);
        viewPagerAdapterTop.addFragment(new TotalExpenseFragment());
        viewPagerAdapterTop.addFragment(new TotalIncomeFragment());
        viewPagerTop.setAdapter(viewPagerAdapterTop); // Gọi setAdapter sau khi khởi tạo

        // Tạo và gán adapter cho ViewPagerBottom
        ViewPagerAdapter viewPagerAdapterBottom = new ViewPagerAdapter(this);
        viewPagerAdapterBottom.addFragment(new ExpenseTabFragment());
        viewPagerAdapterBottom.addFragment(new IncomeTabFragment());
        viewPagerBottom.setAdapter(viewPagerAdapterBottom); // Gọi setAdapter sau khi khởi tạo

        // Khởi tạo Spinner và thiết lập adapter cho Spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Thiết lập sự kiện quay lại
        back = findViewById(R.id.back_financialReport);
        back.setOnClickListener(v -> finish());

        // Bắt sự kiện chọn mục trong Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                boolean isYear = selectedOption.equals("Year");

                // Truyền giá trị isYear vào các Fragment
                Bundle bundle = new Bundle();
                bundle.putBoolean("isYear", isYear);

                // Truyền vào ExpenseTabFragment
                ExpenseTabFragment expenseTabFragment = (ExpenseTabFragment) viewPagerAdapterBottom.getFragment(0);
                expenseTabFragment.setArguments(bundle);
                expenseTabFragment.updateData(isYear);

                // Truyền vào IncomeTabFragment
                IncomeTabFragment incomeTabFragment = (IncomeTabFragment) viewPagerAdapterBottom.getFragment(1);
                incomeTabFragment.setArguments(bundle);
                incomeTabFragment.updateData(isYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Liên kết TabLayout với ViewPager2
        tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPagerBottom, (tab, position) -> {
            if (position == 0) {
                tab.setText("Expense");
            } else if (position == 1) {
                tab.setText("Income");
            }
        }).attach();

        // Đồng bộ ViewPager2 giữa viewPagerTop và viewPagerBottom
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
