package com.example.expensemanagement.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;
import com.example.expensemanagement.sqlite_database.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EditProfileActivity extends AppCompatActivity {
    private boolean ShowNewpassword;
    private boolean ShowNewpasswordAgain;
    private UserDAO userDAO;
    private EditText username , newpassword , newwpasswordAgain;
    private Button btnSave;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userDAO = new UserDAO(this);
        username = findViewById(R.id.edtFistName);
        username.setText(userDAO.getUserName());
        back = findViewById(R.id.backArrowEditProfile);
        btnSave = findViewById(R.id.btnSave);
        //
        newpassword = findViewById(R.id.edtNewpassword);
        newwpasswordAgain = findViewById(R.id.edtNewpasswordAgain);
        //Gắn sự kiện hiển thị/ẩn mật khẩu cho từng EditTex
        setupPasswordToggle(newpassword, true);  // true: cho EnterNewPassword
        setupPasswordToggle(newwpasswordAgain, false);


        btnSave.setOnClickListener(v -> {
            String newUserName = username.getText().toString();
            String newPassword = newpassword.getText().toString();
            String newPasswordAgain = newwpasswordAgain.getText().toString();
            if (newUserName.isEmpty()) {
                Toast.makeText(this , "Please Enter your UserName" , Toast.LENGTH_SHORT).show();
            }
            // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
            if (newPassword.isEmpty() || newPasswordAgain.isEmpty()) {
                Toast.makeText(this, "Upadete profile success", Toast.LENGTH_SHORT).show();
                finish();
                userDAO.updateUserName(newUserName);
            }
            if (!newPasswordAgain.equals(newPassword) ) {
                Toast.makeText(this, "Please enter password and new password match", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                String hashedPassword = hashPassword(newPasswordAgain);
                if (!isValidPassword(newPasswordAgain) || !isValidPassword(newPassword)) {
                    Toast.makeText(this, "Password must be at least 8 characters, include upper & lower case, a number, and a special character", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (hashedPassword != null) {
                        Toast.makeText(this, "Upadete profile success", Toast.LENGTH_SHORT).show();
                        userDAO.updatePassword(hashedPassword);
                        finish();

                    } else {
                        Toast.makeText(this, "Error hashing password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    // xử lý sự kiện hiển thị hoặc ẩn mật khẩu
    private void setupPasswordToggle(EditText editText, boolean isFirst) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Kiểm tra nếu nhấn vào drawableEnd
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawablesRelative()[2].getBounds().width())) {
                    if (isFirst) {
                        ShowNewpassword = !ShowNewpassword;
                        togglePasswordVisibility(editText, ShowNewpassword);
                    } else {
                        ShowNewpasswordAgain = !ShowNewpasswordAgain;
                        togglePasswordVisibility(editText, ShowNewpasswordAgain);
                    }

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
    // Xử lý việc điền mật khẩu không đúng theo định dạng
    private boolean isValidPassword(String password) {
        // Mật khẩu phải có ít nhất 8 ký tự, bao gồm: chữ hoa, chữ thường, số và ký tự đặc biệt
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }

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