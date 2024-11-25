package com.example.expensemanagement.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;

public class SignInActivity extends AppCompatActivity {


    private ImageView back;
    private Button login;
    private EditText email;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        TextView forgotPassword = findViewById(R.id.tvforgotPassword);
        back = findViewById(R.id.backArrow);
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish current activity
                finish();
            }
        });
        // forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this , ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        // login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();
                if (TextUtils.isEmpty(inputEmail) ) {
                    email.setError("Please enter your email");
                }
                else if (TextUtils.isEmpty(inputPassword) ) {
                    password.setError("Please enter your password");
                }
                else {
                    Intent intent = new Intent(SignInActivity.this , OtpAuthenticationActivity.class);
                    startActivity(intent);
                }
            }
        });
        // Tìm TextView bằng ID
        TextView tvSignUp = findViewById(R.id.tvSignIn);

        // Tạo chuỗi văn bản với màu và gạch chân cho phần "Sign Up"
        String text = "Don't have an account yet? Sign Up";
        SpannableString spannableString = new SpannableString(text);

        // Đặt màu xanh cho "Sign Up"
        int start = text.indexOf("Sign Up");
        int end = start + "Sign Up".length();
        spannableString.setSpan(
                new ForegroundColorSpan(Color.parseColor("#7F3DFF")), // Màu xanh cho chữ
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Thêm gạch chân cho "Sign Up"
        spannableString.setSpan(
                new UnderlineSpan(),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        spannableString.setSpan(
                new StyleSpan(android.graphics.Typeface.BOLD), // Đặt chữ đậm
                start, // Vị trí bắt đầu
                end, // Vị trí kết thúc
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        // Gán chuỗi đã chỉnh sửa vào TextView
        tvSignUp.setText(spannableString);

        // Đặt OnClickListener để chuyển sang màn hình SignUpActivity khi nhấn
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}