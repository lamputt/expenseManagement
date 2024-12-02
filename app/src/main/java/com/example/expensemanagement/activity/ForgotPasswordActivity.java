package com.example.expensemanagement.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView backArrow;
    private EditText emailEditText;
    private Button btnContinue;

    // SQLite database instance
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        backArrow = findViewById(R.id.backArrowForgotPassWord);
        emailEditText = findViewById(R.id.etEmail);
        btnContinue = findViewById(R.id.btnContinue);

        // Open the SQLite database (replace "ExpenseManagement" with your actual database name)
        database = openOrCreateDatabase("ExpenseManagement", MODE_PRIVATE, null);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email");
                } else {
                    // Check if the email exists in the database
                    if (isEmailExists(email)) {
                        // Navigate to the next screen if email exists
                        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("email", email); // Pass the email to the next Activity
                        startActivity(intent);
                    } else {
                        // Show error message if email does not exist
                        Toast.makeText(ForgotPasswordActivity.this, "Email not found in our records!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Check if the given email exists in the SQLite database.
     * @param email The email to check.
     * @return True if the email exists, false otherwise.
     */
    private boolean isEmailExists(String email) {
        // Query to check if the email exists in the Users table
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Close the cursor to release resources
        return exists;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection to prevent memory leaks
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
