package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private DatabaseHelper dbHelper;

    public TransactionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Transaction vào database
    public long addTransaction(long user_id , String type , long category_id , long bank_id , String description , double amount , String date ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("type", type);
        values.put("category_id", category_id);
        values.put("bank_id", bank_id);
        values.put("description", description);
        values.put("amount", amount);
        values.put("date", date);
        values.putNull("status");
        long id = db.insert("Transactions", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Transaction
    // Lấy tất cả Transaction
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn kết hợp với bảng Categories để lấy thêm tên danh mục
        String query = "SELECT t.*, c.name AS categoryName " +
                "FROM Transactions t " +
                "JOIN Categories c ON t.category_id = c.id";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Tạo đối tượng Transaction từ kết quả truy vấn
                Transaction transaction = new Transaction(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("bank_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getString(cursor.getColumnIndexOrThrow("categoryName")) // Lấy tên danh mục từ kết quả
                );
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
        db.delete("Transactions", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void resetDataInBanks() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Transactions "); // Xóa tất cả dữ liệu trong bảng banks
        db.close();
    }

    public List<Transaction> getTransactionsByBankId(long bankId) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t.*, c.name AS categoryName " +
                "FROM Transactions t " +
                "JOIN Categories c ON t.category_id = c.id " +
                "WHERE t.bank_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bankId)});

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("bank_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getString(cursor.getColumnIndexOrThrow("categoryName")) // Lấy tên danh mục
                );
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    public List<Transaction> getTransactionsByCategoryId(long categoryId) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t.*, c.name AS categoryName " +
                "FROM Transactions t " +
                "JOIN Categories c ON t.category_id = c.id " +
                "WHERE t.category_id = ? AND t.type = 'expense'";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("bank_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getString(cursor.getColumnIndexOrThrow("categoryName")) // Lấy tên danh mục
                );
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    public double getTotalExpenseByCategory(long categoryId, String dateStart, String dateEnd) {
        double total = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SUM(amount) AS total FROM Transactions " +
                "WHERE category_id = ? AND type = 'expense' AND date BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId), dateStart, dateEnd});

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        db.close();
        return total;
    }






}
