package com.example.expensemanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;

public class ForgotPasswordActivity extends AppCompatActivity {

    private UserDAO userDAO;

    private ImageView backArrow;
    private EditText emailEditText;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(this);
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
        btnContinue.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "please, enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ForgotPasswordActivity.this, "Email not is valid", Toast.LENGTH_SHORT).show();
                return;
            }
            userDAO.forgotPassword(email);

            Intent intent = new Intent(ForgotPasswordActivity.this, OtpAuthenticationActivity.class);
            intent.putExtra("forgotPassword", true);
            startActivity(intent);
            finish();
        });
    }
}