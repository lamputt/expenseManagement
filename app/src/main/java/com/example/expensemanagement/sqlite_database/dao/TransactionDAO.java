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
    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", transaction.getUserId());
        values.put("type", transaction.getType());
        values.put("category_id", transaction.getCategoryId());
        values.put("bank_id", transaction.getBankId());
        values.put("description", transaction.getDescription());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("status", transaction.getStatus());
        long id = db.insert("transaction", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Transaction
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Transactions", null);
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
                        cursor.getString(cursor.getColumnIndexOrThrow("status"))
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
        db.delete("transaction", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
