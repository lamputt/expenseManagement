package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;
import com.example.expensemanagement.databinding.ActivityMainBinding;

public class OtpAuthenSuccessActivity extends AppCompatActivity {
    private Button buttonStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập layout cho Activity
        setContentView(R.layout.otp_authen_success);

        // Ánh xạ Button
        buttonStart = findViewById(R.id.btnStart);

        // Thiết lập sự kiện OnClickListener
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn MainActivity
                Intent intent = new Intent(OtpAuthenSuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}