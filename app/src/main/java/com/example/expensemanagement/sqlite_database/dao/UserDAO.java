package com.example.expensemanagement.sqlite_database.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final DatabaseHelper dbHelper;
    private final Context context;

    // Constructor nhận context
    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm User vào database
    public long addUser(String userName, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        values.put("password", password);
        values.put("email", email);
        values.put("created_at", System.currentTimeMillis());

        long id = db.insert("users", null, values);
        db.close();
        return id;
    }

    // Kiểm tra login
    public boolean checkLogin(String inputEmail, String inputPassword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean result;
        String[] projection = {
                "id"  // Chỉ lấy id
        };
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {inputEmail, inputPassword};
        try {
            Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);


            result = cursor.getCount() > 0;
            if (result && cursor.moveToFirst()) {
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
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                        cursor.getString(cursor.getColumnIndexOrThrow("first_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("last_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                        cursor.getString(cursor.getColumnIndexOrThrow("updated_at")),
                        cursor.getString(cursor.getColumnIndexOrThrow("deleted_at"))
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
}
