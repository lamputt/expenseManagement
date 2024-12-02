package com.example.expensemanagement.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;

import java.security.MessageDigest;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etPassword, etConfirmPassword;
    private Button btnResetPassword;
    private SQLiteDatabase database;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        // Kết nối các views
        etPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etRetypeNewPassword);
        btnResetPassword = findViewById(R.id.btnContinue);

        // Nhận email từ màn hình Forgot Password
        email = getIntent().getStringExtra("email");

        // Mở cơ sở dữ liệu SQLite
        database = openOrCreateDatabase("ExpenseManagement", MODE_PRIVATE, null);

        // Xử lý khi người dùng nhấn nút Reset Password
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // Kiểm tra các điều kiện đầu vào
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Hash mật khẩu mới và cập nhật vào SQLite
                    String hashedPassword = hashPassword(password);
                    updatePassword(email, hashedPassword);
                    Toast.makeText(ResetPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                    finish();  // Đóng màn hình và quay lại màn hình trước đó
                }
            }
        });
    }

    // Hàm hash mật khẩu sử dụng SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm cập nhật mật khẩu mới vào cơ sở dữ liệu SQLite
    private void updatePassword(String email, String hashedPassword) {
        // Cập nhật mật khẩu cho người dùng trong bảng Users
        database.execSQL("UPDATE Users SET password = ? WHERE email = ?", new Object[]{hashedPassword, email});
    }
}
