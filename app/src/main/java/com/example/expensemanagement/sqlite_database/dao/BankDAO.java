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
    private  DatabaseHelper dbHelper;

    public BankDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm Bank
    public long addBank(String bankName , String accountNumber , Double amount  ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bankName);
        values.put("account_number", accountNumber);
        values.putNull("account_type");
        values.put("amount", amount);
        values.putNull("created_at");
        values.putNull("updated_at");
        values.putNull("deleted_at");
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

    public boolean isBankExist(String bankName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean exists = false;

        String query = "SELECT id FROM banks WHERE name = ? AND deleted_at IS NULL";
        try (Cursor cursor = db.rawQuery(query, new String[]{bankName})) {
            exists = cursor.moveToFirst(); // Kiểm tra có bản ghi trả về
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Đảm bảo đóng kết nối
        }

        return exists;
    }
    public void resetDataInBanks() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM banks"); // Xóa tất cả dữ liệu trong bảng banks
        db.close();
    }
    public Bank getBankById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM banks WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
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
            cursor.close();
            db.close();
            return bank;
        }
        cursor.close();
        db.close();
        return null;
    }
    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }


}
