package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Acount;
import com.example.expensemanagement.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    private DatabaseHelper dbHelper;

    public AccountDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Account vào database
    public long addAccount(Acount acount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("role", Constant.Role.USER.toString());
        values.put("user_id", acount.getUserId());
        long id = db.insert("account", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Account
    public List<Acount> getAllAccounts() {
        List<Acount> accountList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account", null);
        if (cursor.moveToFirst()) {
            do {
                Acount acount = new Acount(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("role")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("user_id"))
                );
                accountList.add(acount);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }
    // Tìm Account theo user_id
    public Acount getAccountByUserId(long userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            Acount acount = new Acount(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("user_id"))
            );
            cursor.close();
            db.close();
            return acount;
        }
        return null;
    }
}
