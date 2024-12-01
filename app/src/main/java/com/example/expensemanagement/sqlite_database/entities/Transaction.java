package com.example.expensemanagement.sqlite_database.entities;

public class Transaction {
    private long id;
    private long userId;
    private String type;
    private long categoryId;
    private long bankId;
    private String description;
    private double amount;
    private String date;
    private String status;
    private String categoryName; // Thêm thuộc tính này

    // Constructor
    public Transaction(long id, long userId, String type, long categoryId, long bankId, String description, double amount, String date, String status, String categoryName) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.categoryId = categoryId;
        this.bankId = bankId;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.categoryName = categoryName; // Gán giá trị
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    public long getBankId() { return bankId; }
    public void setBankId(long bankId) { this.bankId = bankId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCategoryName() { return categoryName; } // Getter cho categoryName
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; } // Setter
}
