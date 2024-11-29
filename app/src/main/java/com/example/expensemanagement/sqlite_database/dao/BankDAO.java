package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankDAO {
    private DatabaseHelper dbHelper;

    public BankDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Bank
    public long addBank(String bankName , String accountNumber , String amount  ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bankName);
        values.put("account_number", accountNumber);
        values.put("account_type", "");
        values.put("amount", amount);
        values.put("created_at", "");
        values.put("updated_at", "");
        values.put("deleted_at", "");
        long id = db.insert("banks", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Banks
    public List<Bank> getAllBanks() {
        List<Bank> bankList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM banks", null);
        if (cursor.moveToFirst()) {
            do {
                Bank bank = new Bank(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("account_number")),
                        cursor.getString(cursor.getColumnIndexOrThrow("account_type")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                        cursor.getString(cursor.getColumnIndexOrThrow("updated_at")),
                        cursor.getString(cursor.getColumnIndexOrThrow("deleted_at"))
                );
                bankList.add(bank);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bankList;
    }

    // Xóa Bank theo ID
    public void deleteBank(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("banks", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
