package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private DatabaseHelper dbHelper;
    private Context context;

    public BudgetDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Budget vào database
    public long addBudget(Budget budget) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        ContentValues values = new ContentValues();
        values.put("category_id", budget.getCategoryId());
        values.put("user_id", userId);
        values.put("amount", budget.getAmount());
        values.put("date_start", budget.getDateStart());
        values.put("date_end", budget.getDateEnd());
        values.put("status", budget.getStatus());
        long id = db.insert("budgets", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Budgets
    public List<Budget> getAllBudgets() {
        List<Budget> budgetList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM budgets";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date_start")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date_end")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status"))
                );
                budgetList.add(budget);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return budgetList;
    }

    // Xóa Budget theo ID
    public void deleteBudget(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("budgets", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
