package com.example.expensemanagement.sqlite_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensemanagement.Model.ItemDateTransaction;
import com.example.expensemanagement.sqlite_database.DatabaseHelper;
import com.example.expensemanagement.sqlite_database.entities.Bank;
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("type", transaction.getType());
        values.put("category_id", transaction.getCategoryId());
        values.put("bank_id", transaction.getBankId());
        values.put("description", transaction.getDescription());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        long id = db.insert("transactions", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả Transaction theo user_id
    public List<Transaction> getAllTransactions() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn với JOIN giữa bảng transaction và categories
        String query = "SELECT " +
                "transactions.*, " +
                "categories.id as category_id, " +
                "categories.name, " +
                "categories.description as category_description" +
                " FROM transactions " +
                "JOIN categories " +
                "ON transactions.category_id = categories.id " +
                "WHERE transactions.user_id = ?" +
                "ORDER BY transactions.date ASC";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category_description"))
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
        db.delete("Transactions", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void resetDataInBanks() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Transactions "); // Xóa tất cả dữ liệu trong bảng banks
        db.close();
    }

    public double getTotalExpense() {
        double totalExpense = 0.0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SUM(amount) AS total_expense FROM transactions WHERE type = ? AND transactions.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"expense", String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            totalExpense = cursor.getDouble(cursor.getColumnIndexOrThrow("total_expense"));
        }

        cursor.close();
        db.close();

        return totalExpense;
    }

    public double getTotalIncome() {
        double totalExpense = 0.0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SUM(amount) AS total_income FROM transactions WHERE type = ? AND transactions.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"income", String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            totalExpense = cursor.getDouble(cursor.getColumnIndexOrThrow("total_income"));
        }

        cursor.close();
        db.close();

        return totalExpense;
    }

    public List<ItemDateTransaction> getDataByDate(String type) {
        List<ItemDateTransaction> data = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        // Query để lấy tổng tiền theo tháng
        String query = "SELECT strftime('%Y-%m', date) AS month, SUM(amount) AS total_amount " +
                "FROM transactions " +
                "WHERE transactions.user_id = ?" +
                "AND transactions.type = ? " +
                "GROUP BY strftime('%Y-%m', date);";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), type});

        // Duyệt qua các kết quả và ánh xạ vào danh sách
        if (cursor.moveToFirst()) {
            do {
                // Lấy tháng dưới dạng "yyyy-MM" (ví dụ "2024-11")
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                // Lấy tổng số tiền
                String totalAmount = String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount")));

                // Chuyển đổi tháng từ dạng "yyyy-MM" thành dạng chữ (ví dụ "November 2024")
                String monthText = convertMonthNumberToText(month);

                // Tạo một đối tượng ItemDateTransaction và thêm vào danh sách
                ItemDateTransaction item = new ItemDateTransaction(monthText, totalAmount);
                data.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;
    }

    // Phương thức chuyển đổi tháng từ dạng "yyyy-MM" thành dạng chữ
    public String convertMonthNumberToText(String monthYear) {
        // Mảng chứa tên các tháng
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        // Tách tháng và năm từ chuỗi "yyyy-MM"
        String[] parts = monthYear.split("-");
        int monthNumber = Integer.parseInt(parts[1]) - 1;  // Tháng bắt đầu từ 0, vì vậy phải trừ đi 1

        return months[monthNumber];  // Ví dụ "November 2024"
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
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category_description"))
                );

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
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category_description"))
                );

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
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        String query = "SELECT category_id, amount AS total " +
                "FROM Transactions " +
                "WHERE user_id = ? " +
                "AND category_id = ? " +
                "AND type = 'expense' " +
                "AND date BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(categoryId), dateStart, dateEnd});

        if (cursor.moveToFirst()) {
            do {
                total += cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
                int categoryIdIndex = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }


}
