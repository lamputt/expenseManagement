package com.example.expensemanagement.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;
import com.example.expensemanagement.utils.ToastUtil;

public class OtpAuthenticationActivity extends AppCompatActivity {

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_authentication);
        userDAO = new UserDAO(this);

        // AppBar Back Button
        ImageView backArrowSignIn = findViewById(R.id.backArrowSignIn);
        backArrowSignIn.setOnClickListener(v -> onBackPressed());

        // Khai báo các ô nhập liệu
        EditText otpBox1 = findViewById(R.id.otpBox1);
        EditText otpBox2 = findViewById(R.id.otpBox2);
        EditText otpBox3 = findViewById(R.id.otpBox3);
        EditText otpBox4 = findViewById(R.id.otpBox4);

        EditText[] otpBoxes = {otpBox1, otpBox2, otpBox3, otpBox4};

        // Focus vào ô đầu tiên và hiển thị bàn phím sau khi giao diện đã sẵn sàng
        otpBox1.requestFocus();
        otpBox1.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(otpBox1, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200); // Thêm độ trễ nhỏ để đảm bảo giao diện đã sẵn sàng

        // Thêm TextWatcher cho từng ô
        for (int i = 0; i < otpBoxes.length; i++) {
            int currentIndex = i; // Biến cục bộ để sử dụng trong TextWatcher
            otpBoxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && currentIndex < otpBoxes.length - 1) {
                        // Chuyển sang ô tiếp theo
                        otpBoxes[currentIndex + 1].requestFocus();
                    } else if (s.length() == 0 && currentIndex > 0) {
                        // Chuyển lại ô trước đó khi nhấn "Xóa"
                        otpBoxes[currentIndex - 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        // Xử lý TextView "Haven't received the OTP?"
        TextView tvResendOtp = findViewById(R.id.tvResendOtp);
        tvResendOtp.setOnClickListener(v ->
                ToastUtil.showCustomToast(OtpAuthenticationActivity.this, "Request to resend OTP was successful!", R.drawable.success_toast)
        );

        // Xử lý nút Continue
        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            // Lấy OTP từ các ô
            String otp = otpBox1.getText().toString() +
                    otpBox2.getText().toString() +
                    otpBox3.getText().toString() +
                    otpBox4.getText().toString();

            String email = getIntent().getStringExtra("email");
            String name = getIntent().getStringExtra("name");
            String password = getIntent().getStringExtra("password");
            String hashPassword = getIntent().getStringExtra("hashedPassword");

            // Kiểm tra OTP
            boolean isVerify = userDAO.checkOtp(otp);

            if (isVerify) {
                // Lưu thông tin vào SQLite
                userDAO.addUser(name, email, password, hashPassword);
                ToastUtil.showCustomToast(OtpAuthenticationActivity.this, "Valid OTP!", R.drawable.success_toast);

                // xóa transId trong sharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("OtpPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("transId");
                editor.apply();

                // Chuyển sang màn hình chính
                Intent intent = new Intent(OtpAuthenticationActivity.this, OtpAuthenSuccessActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.showCustomToast(OtpAuthenticationActivity.this, "Invalid OTP!", R.drawable.warning_toast);
            }
        });
    }
}
