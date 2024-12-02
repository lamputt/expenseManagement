package com.example.expensemanagement.sqlite_database.entities;

public class Bank {
    private long id;
    private String name;
    private String accountNumber;
    private String accountType;
    private double amount;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    // Constructor
    public Bank(long id, String name, String accountNumber, String accountType, double amount, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Bank(long bankId, String name) {
        this.id = bankId;
        this.name = name;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getDeletedAt() { return deletedAt; }
    public void setDeletedAt(String deletedAt) { this.deletedAt = deletedAt; }
}
