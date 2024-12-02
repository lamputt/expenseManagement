package com.example.expensemanagement.Model;

public class ItemTransaction {

    private final String category;
    private final String type;
    private final String description;
    private final String price;
    private final String time;

    public ItemTransaction(String category, String type, String description, String price, String time) {
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

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

}
