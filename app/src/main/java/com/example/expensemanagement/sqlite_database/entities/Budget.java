package com.example.expensemanagement.sqlite_database.entities;

public class Budget {
    private long id;
    private long categoryId;
    private long userId;
    private double amount;
    private String dateStart;
    private String dateEnd;
    private String status;

    // Constructor
    public Budget(long id, long categoryId, long userId, double amount, String dateStart, String dateEnd, String status) {
        this.id = id;
        this.categoryId = categoryId;
        this.userId = userId;
        this.amount = amount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDateStart() { return dateStart; }
    public void setDateStart(String dateStart) { this.dateStart = dateStart; }

    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
