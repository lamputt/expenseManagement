package com.example.expensemanagement.activity;

import android.os.Bundle;

import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import com.example.expensemanagement.OnboardingAdapter;
import com.example.expensemanagement.R;
import com.google.android.material.tabs.TabLayout;

public class OnboardingActivity extends AppCompatActivity {

    private final int[] layouts = {R.layout.onboarding_slide1, R.layout.onboarding_slide2, R.layout.onboarding_slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabIndicator = findViewById(R.id.tabIndicator);

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(this, layouts);
        viewPager.setAdapter(onboardingAdapter);
        tabIndicator.setupWithViewPager(viewPager);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(v -> {
            // Xử lý chuyển đến màn hình đăng ký
        });

        btnLogin.setOnClickListener(v -> {
            // Xử lý chuyển đến màn hình đăng nhập
        });
    }
}
