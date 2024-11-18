package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView backArrow;
    private EditText emailEditText;
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        backArrow = findViewById(R.id.backArrowForgotPassWord);
        emailEditText = findViewById(R.id.etEmail);
        btnContinue = findViewById(R.id.btnContinue);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email");
                }
                else {
                    Intent intent = new Intent(ForgotPasswordActivity.this , SendEmailSuccessActivity.class);
                    startActivity(intent);
                }
            }
        });
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}