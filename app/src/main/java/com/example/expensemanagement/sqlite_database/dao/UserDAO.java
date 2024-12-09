package com.example.expensemanagement.sqlite_database.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.controller.AccountController;
import com.example.expensemanagement.sqlite_database.controller.AuthenticationController;
import com.example.expensemanagement.sqlite_database.controller.OtpController;
import com.example.expensemanagement.sqlite_database.dto.request.RegisterRequest;
import com.example.expensemanagement.sqlite_database.dto.request.UpdateAccountRequest;
import com.example.expensemanagement.sqlite_database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final DatabaseHelper dbHelper;
    private final Context context;
    private final AuthenticationController authenticationController;
    private final OtpController otpController;
    private final AccountController accountController;

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        authenticationController = new AuthenticationController(context);
        otpController = new OtpController(context);
        accountController = new AccountController(context);
    }

    // Thêm User vào database
    public long addUser(String userName, String email, String password, String hashPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("OtpPrefs", MODE_PRIVATE);
        String transId = sharedPreferences.getString("transId", null);

        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        values.put("password", hashPassword);
        values.put("email", email);
        values.put("created_at", System.currentTimeMillis());

        RegisterRequest registerRequest = new RegisterRequest(transId, email, userName, password);
        authenticationController.register(registerRequest);

        long id = db.insert("users", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả User
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("user_name")),  // sửa tên cột từ userName sang user_name
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at"))
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Validate User by email and hashed password
    public boolean validateUser(String email, String hashedPassword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            Cursor cursor = db.rawQuery(query, new String[]{email, hashedPassword});

            authenticationController.authenticate(email, hashedPassword);

            boolean isValid = cursor.moveToFirst();
            if (isValid) {
                // Lấy user_id từ cursor
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                // Lưu user_id vào SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_id", userId);  // Lưu ID người dùng
                editor.apply();
            }
            cursor.close();
            db.close();

            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getUserName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        if (userId == -1) {
            return null;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT user_name FROM users WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
        }
        cursor.close();
        db.close();
        return userName;
    }

    public int updateUserName( String newUserName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", newUserName);

        // Điều kiện cập nhật dựa trên id
        int rowsAffected = db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected; // Trả về số dòng đã được cập nhật
    }

    public int updatePassword(String newPassword) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int rowsAffected = db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});

        accountController.updateAccount(new UpdateAccountRequest(newPassword));
        db.close();
        return rowsAffected;
    }

    public void checkEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean exists = cursor.moveToFirst();
        if (exists) {
            return;
        } else {
            otpController.sendOtp(email);

        }
        cursor.close();
        db.close();

    }

    public boolean checkOtp(String otp) {
        otpController.verifyOtp(otp);
        SharedPreferences sharedPreferences = context.getSharedPreferences("OtpPrefs", MODE_PRIVATE);
        String serverOtp = sharedPreferences.getString("otp", null);
        return serverOtp != null && serverOtp.equals(otp);
    }

    public void forgotPassword(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean exists = cursor.moveToFirst();
        if (exists) {
            otpController.sendOtp(email);
        } else {
            return;
        }
        cursor.close();
        db.close();

    }
}
