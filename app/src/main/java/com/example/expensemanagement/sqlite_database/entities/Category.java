package com.example.expensemanagement.sqlite_database.entities;

public class Category {
    private long id;
    private String name;
    private String description;

    // Constructor
    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters và Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
