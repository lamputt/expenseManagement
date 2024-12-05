package com.example.expensemanagement.Model;

public class ItemTransaction {

    private final String category;
    private final String type;
    private final String description;
    private final double price;
    private final String time;

    public ItemTransaction(String category, String type, String description, double price, String time) {
        this.category = category;
        this.type = type;
        this.description = description;
        this.price = price;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

}
