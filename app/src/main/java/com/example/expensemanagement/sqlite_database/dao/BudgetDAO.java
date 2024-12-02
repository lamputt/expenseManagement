package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private DatabaseHelper dbHelper;

    public BudgetDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Budget vào database
    public long addBudget(long Category_id , long user_id , double amount , String dateStart , String dateEnd ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_id", Category_id);
        values.put("user_id", user_id);
        values.put("amount", amount );
        values.put("date_start" , dateStart);
        values.put("date_end" , dateEnd);
        values.putNull("status");
        long id = db.insert("budgets", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Budgets
    public List<Budget> getAllBudgets() {
        List<Budget> budgetList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM budgets", null);
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

    public String getCategoryNameById(long categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT name FROM categories WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
        String categoryName = null;

        if (cursor.moveToFirst()) {
            categoryName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        }
        cursor.close();
        db.close();

        return categoryName;
    }

    public boolean isCategoryExist(long categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM budgets WHERE category_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0; // Nếu có ít nhất một bản ghi thì Category đã tồn tại
        }
        cursor.close();
        db.close();
        return exists;
    }
}
