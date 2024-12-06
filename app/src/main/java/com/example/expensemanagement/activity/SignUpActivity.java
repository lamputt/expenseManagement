package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;
import com.example.expensemanagement.utils.ToastUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private EditText nameEditText, emailEditText, passwordEditText;
    private Button signUpButton;
    private ImageView backArrow;
    private boolean showPassWord;

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

        setupPasswordToggle(passwordEditText);
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
                    ToastUtil.showCustomToast(SignUpActivity.this, "Please fill all fields", R.drawable.warning_toast);
                } else if (!isValidEmail(email)) {
                    ToastUtil.showCustomToast(SignUpActivity.this, "Invalid email format", R.drawable.warning_toast);
                } else if (!isValidPassword(password)) {
                    ToastUtil.showCustomToast(SignUpActivity.this, "Password must be at least 8 characters, include upper & lower case, a number, and a special character", R.drawable.warning_toast);
                } else {
                    // Hash password
                    String hashedPassword = hashPassword(password);

                    if (hashedPassword != null) {
                        // Lưu vào SQLite
                        long result = userDAO.addUser(name, email, hashedPassword);
                        if (result != -1) {
                            ToastUtil.showCustomToast(SignUpActivity.this, "Sign up successful! Data saved locally!", R.drawable.success_toast);
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish(); // Đóng màn hình đăng ký
                        } else {
                            ToastUtil.showCustomToast(SignUpActivity.this, "Failed to save data!", R.drawable.warning_toast);
                        }
                    } else {
                        ToastUtil.showCustomToast(SignUpActivity.this, "Error hashing password!", R.drawable.warning_toast);
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
    private void setupPasswordToggle(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Kiểm tra nếu nhấn vào drawableEnd
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawablesRelative()[2].getBounds().width())) {
                    showPassWord = !showPassWord;
                    togglePasswordVisibility(editText ,showPassWord );
                    // Gọi performClick() để hỗ trợ accessibility
                    v.performClick();
                    return true;
                }
            }
            return false;
        });
    }

    private void togglePasswordVisibility(EditText editText, boolean isPasswordVisible) {
        if (isPasswordVisible) {
            // Hiển thị mật khẩu
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, getResources().getDrawable(R.drawable.showpassword, null), null
            );
        } else {
            // Ẩn mật khẩu
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, getResources().getDrawable(R.drawable.showpassword, null), null
            );
        }
        // Đặt con trỏ ở cuối văn bản
        editText.setSelection(editText.getText().length());
    }
}
