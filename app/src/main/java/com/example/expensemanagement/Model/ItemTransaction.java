package com.example.expensemanagement.Model;

public class ItemTransaction {

    private String category;
    private String type;
    private String description;
    private String price;
    private String time;

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
