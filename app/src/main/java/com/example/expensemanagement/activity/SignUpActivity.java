package com.example.expensemanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.dao.AccountDAO;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;

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
                // Finish current activity
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

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform sign up logic here (e.g., API call)
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                }

                // Lưu vào SQLite
                long result = userDAO.addUser(name, email,password);
                if (result != -1) {
                    Toast.makeText(SignUpActivity.this, "Data saved locally!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
