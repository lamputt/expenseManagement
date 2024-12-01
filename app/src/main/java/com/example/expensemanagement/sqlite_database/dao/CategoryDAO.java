package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Bank;
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

    public void deleteCategory(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("categories", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void resetCategory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM categories"); // Xóa tất cả dữ liệu trong bảng banks
        db.close();
    }

    public Category getCategoryById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.id, c.name, c.description, COALESCE(SUM(t.amount), 0) AS total_spent " +
                "FROM categories c " +
                "LEFT JOIN Transactions t ON c.id = t.category_id " +
                "WHERE c.id = ? " +
                "GROUP BY c.id, c.name, c.description", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Category category = new Category(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("total_spent"))
            );
            cursor.close();
            db.close();
            return category;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Category> getAllCategoriesByExpense() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c.id, c.name, c.description, \n" +
                "       COALESCE(SUM(t.amount), 0) AS total_spent \n" +
                "FROM categories c \n" +
                "LEFT JOIN Transactions t ON c.id = t.category_id AND t.type = 'expense' \n" + // Thêm điều kiện t.type = 'expense'
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
    // Lấy các Category theo id và chỉ tính các giao dịch có type = 'expense'
    // Lấy một Category theo id và chỉ tính các giao dịch có type = 'expense'
    public Category getCategoryByIdTypeExpense(long id) {
        Category category = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn lấy một Category theo id và chỉ tính các giao dịch có type = 'expense'
        String query = "SELECT c.id, c.name, c.description, \n" +
                "       COALESCE(SUM(t.amount), 0) AS total_spent \n" +
                "FROM categories c \n" +
                "LEFT JOIN Transactions t ON c.id = t.category_id AND t.type = 'expense' \n" + // Thêm điều kiện t.type = 'expense'
                "WHERE c.id = ? \n" + // Lọc theo id
                "GROUP BY c.id, c.name, c.description";

        // Truy vấn với tham số id
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            // Tạo đối tượng Category duy nhất từ kết quả truy vấn
            category = new Category(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("total_spent"))
            );
        }

        cursor.close();
        db.close();
        return category;
    }


}
