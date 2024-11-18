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

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private CheckBox checkBoxAgreeTerms;
    private Button signUpButton;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                } else if (!checkBoxAgreeTerms.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "You must agree to the terms", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform sign up logic here (e.g., API call)
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
