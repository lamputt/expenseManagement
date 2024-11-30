package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Category vào database
    public long addCategory(String name, String description ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        long id = db.insert("categories", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Categories
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.id, c.name, c.description, \n" +
                "       COALESCE(SUM(t.amount), 0) AS total_spent \n" +
                "FROM categories c \n" +
                "LEFT JOIN Transactions t ON c.id = t.category_id \n" +
                "GROUP BY c.id, c.name, c.description", null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("total_spent"))
                );
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }
}
