package com.example.expensemanagement.sqlite_database.entities;

public class Acount {
    private long id;
    private String role;
    private long userId;

    // Constructor
    public Acount(long id, String role, long userId) {
        this.id = id;
        this.role = role;
        this.userId = userId;
    }

    // Getters và Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}
