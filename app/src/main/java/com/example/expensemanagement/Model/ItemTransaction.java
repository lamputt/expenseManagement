package com.example.expensemanagement.Model;

public class ItemTransaction {

    private String category;
    private String description;
    private String price;
    private String time;

    public ItemTransaction(String category, String description, String price, String time) {
        this.category = category;
        this.description = description;
        this.price = price;
        this.time = time;
    }

    public String getCategory() {
        return category;
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
