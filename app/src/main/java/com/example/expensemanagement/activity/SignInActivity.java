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

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;

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

        back.setOnClickListener(v -> finish());

        // Forgot password
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Login logic
        login.setOnClickListener(v -> {
            String inputEmail = email.getText().toString();
            String inputPassword = password.getText().toString();

            if (TextUtils.isEmpty(inputEmail)) {
                email.setError("Please enter your email");
            } else if (TextUtils.isEmpty(inputPassword)) {
                password.setError("Please enter your password");
            } else {
                // Chuyển đổi mật khẩu sang dạng hashed
                String hashedPassword = hashPassword(inputPassword);

                // Validate user với hashed password
                UserDAO userDAO = new UserDAO(SignInActivity.this);
                boolean isValidUser = userDAO.validateUser(inputEmail, hashedPassword);

                if (isValidUser) {
                    // Đăng nhập thành công
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng nhập thất bại
                    password.setError("Invalid email or password");
                }
            }
        });

        // Handle "Sign Up" link
        TextView tvSignUp = findViewById(R.id.tvSignIn);
        String text = "Don't have an account yet? Sign Up";
        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf("Sign Up");
        int end = start + "Sign Up".length();

        spannableString.setSpan(
                new ForegroundColorSpan(Color.parseColor("#7F3DFF")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableString.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(
                new StyleSpan(android.graphics.Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvSignUp.setText(spannableString);
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    // Hàm chuyển đổi mật khẩu sang hash (SHA-256)
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
