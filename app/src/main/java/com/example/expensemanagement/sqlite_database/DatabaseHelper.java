package com.example.expensemanagement.sqlite_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensemanagement.utils.Constant;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    // Tạo bảng users
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_name TEXT, " +
                    "password TEXT, " +
                    "first_name TEXT, " +
                    "last_name TEXT, " +
                    "email TEXT, " +
                    "created_at TIMESTAMP, " +
                    "updated_at TIMESTAMP, " +
                    "deleted_at TIMESTAMP" +
                    ");";

    // Tạo bảng account
    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "role TEXT, " +
                    "user_id INTEGER, " +
                    "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ");";

    // Tạo bảng categories
    private static final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "description TEXT" +
                    ");";

    // Tạo bảng banks
    private static final String CREATE_TABLE_BANKS =
            "CREATE TABLE banks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "account_number TEXT, " +
                    "account_type TEXT, " +
                    "amount REAL, " +
                    "created_at TIMESTAMP, " +
                    "updated_at TIMESTAMP, " +
                    "deleted_at TIMESTAMP" +
                    ");";

    // Tạo bảng transaction
    private static final String CREATE_TABLE_TRANSACTION =
            "CREATE TABLE Transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, " +
                    "type TEXT, " +
                    "category_id INTEGER, " +
                    "bank_id INTEGER, " +
                    "description TEXT, " +
                    "amount REAL, " +
                    "date TIMESTAMP, " +
                    "status TEXT, " +
                    "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(bank_id) REFERENCES banks(id) ON DELETE CASCADE" +
                    ");";

    // Tạo bảng budgets
    private static final String CREATE_TABLE_BUDGETS =
            "CREATE TABLE budgets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "category_id INTEGER, " +
                    "user_id INTEGER, " +
                    "amount REAL, " +
                    "date_start TIMESTAMP, " +
                    "date_end TIMESTAMP, " +
                    "status TEXT, " +
                    "FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ");";

    public void insertSampleData(SQLiteDatabase db) {
        // Bảng users
        db.execSQL("INSERT INTO users (user_name, password, first_name, last_name, email, created_at) " +
                "VALUES ('john_doe', '924a4ee358e0ab428f75085d2174e4767c927dc3fc61b5ff3f62b15c89e9e368', 'John', 'Doe', 'john.doe@example.com', datetime('now'));");

        // Bảng account
        db.execSQL("INSERT INTO account (role, user_id) " +
                "VALUES ('admin', 1);");

        // Bảng categories
        db.execSQL("INSERT INTO categories (name, description) " +
                "VALUES ('Food', 'Expenses on food and dining'), ('Transport', 'Expenses on travel and transport');");

        // Bảng banks
        db.execSQL("INSERT INTO banks (name, account_number, account_type, amount, created_at) " +
                "VALUES ('Bank of America', '1234567890', 'Savings', 5000.00, datetime('now'));");

        // Bảng transaction
        db.execSQL("INSERT INTO Transactions (user_id, type, category_id, bank_id, description, amount, date, status) " +
                "VALUES (1, 'expense', 1, 1, 'Lunch at restaurant', 20.50, datetime('now'), 'completed');");

        // Bảng budgets
        db.execSQL("INSERT INTO budgets (category_id, user_id, amount, date_start, date_end, status) " +
                "VALUES (1, 1, 300.00, datetime('now', '-30 days'), datetime('now', '+30 days'), 'active');");
    }


    public DatabaseHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;"); // Bật khóa phụ
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_BANKS);
        db.execSQL(CREATE_TABLE_TRANSACTION);
        db.execSQL(CREATE_TABLE_BUDGETS);

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS budgets");
            db.execSQL("DROP TABLE IF EXISTS Transactions");
            db.execSQL("DROP TABLE IF EXISTS banks");
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS account");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
    }
    public Context getContext() {
        return context;  // Trả về context khi cần
    }
}
