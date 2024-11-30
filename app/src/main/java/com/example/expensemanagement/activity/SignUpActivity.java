package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private EditText nameEditText, emailEditText, passwordEditText;
    private Button signUpButton;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(this);
        setContentView(R.layout.sign_up);

        // Initialize views
        backArrow = findViewById(R.id.backArrowSignUp);
        nameEditText = findViewById(R.id.etName);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        signUpButton = findViewById(R.id.btnSignUp);

        // Handle back arrow click
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Handle sign up button click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Kiểm tra các trường hợp nhập
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters, include upper & lower case, a number, and a special character", Toast.LENGTH_SHORT).show();
                } else {
                    // Hash password
                    String hashedPassword = hashPassword(password);

                    if (hashedPassword != null) {
                        // Lưu vào SQLite
                        long result = userDAO.addUser(name, email, hashedPassword);
                        if (result != -1) {
                            Toast.makeText(SignUpActivity.this, "Sign up successful! Data saved locally!", Toast.LENGTH_SHORT).show();

                            // Chuyển đến màn hình đăng nhập sau khi đăng ký thành công
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish(); // Đóng màn hình đăng ký
                        } else {
                            Toast.makeText(SignUpActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error hashing password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Hàm kiểm tra định dạng email.
     */
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Hàm kiểm tra điều kiện mật khẩu.
     */
    private boolean isValidPassword(String password) {
        // Mật khẩu phải có ít nhất 8 ký tự, bao gồm: chữ hoa, chữ thường, số và ký tự đặc biệt
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }

    /**
     * Hàm chuyển đổi mật khẩu thành hash (SHA-256).
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
