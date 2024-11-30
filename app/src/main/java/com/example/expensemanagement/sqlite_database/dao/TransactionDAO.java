package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Category;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private DatabaseHelper dbHelper;
    private Context context;

    public TransactionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    // Thêm Transaction vào database
    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", transaction.getUserId());
        values.put("type", transaction.getType());
        values.put("category_id", transaction.getCategory().getId());
        values.put("bank_id", transaction.getBankId());
        values.put("description", transaction.getDescription());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("status", transaction.getStatus());
        long id = db.insert("transaction", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Transaction theo user_id
    public List<Transaction> getAllTransactions() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "transaction.id", "transaction.user_id", "transaction.type", "transaction.category_id",
                "transaction.bank_id", "transaction.description", "transaction.amount", "transaction.date",
                "transaction.status", "categories.category_name" // Thêm tên category từ bảng categories
        };

        // Truy vấn với JOIN giữa bảng transaction và categories
        String query = "SELECT transactions.*, categories.name FROM transactions " +
                "JOIN categories ON transactions.category_id = categories.id WHERE transactions.user_id = ?";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description"))
                );
                // Lấy dữ liệu từ bảng transaction và bảng categories
                Transaction transaction = new Transaction(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        category,
                        cursor.getLong(cursor.getColumnIndexOrThrow("bank_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status"))
                );

                // Lấy thêm thông tin category_name từ bảng categories
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                // Cập nhật thông tin category cho transaction (nếu cần)
//                transaction.setCategoryName(categoryName);

                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    // Xóa Transaction
    public void deleteTransaction(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("transaction", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
