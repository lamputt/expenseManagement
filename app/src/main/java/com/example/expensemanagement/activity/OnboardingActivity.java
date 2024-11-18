package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
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

        Button btnSignUp = findViewById(R.id.btnSignUpOnboarding);
        Button btnLogin = findViewById(R.id.btnLoginOnboarding);

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(OnboardingActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }
}
