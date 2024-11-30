package com.example.expensemanagement.Model;

public class ItemDateTransaction {
    private String Date ;
    private String Price;

    public ItemDateTransaction (String date , String price) {
        this.Date  = date;
        this.Price = price;
    }

    public String getDate() {
        return this.Date;
    }

    public String getPrice () {
        return this.Price;
    }

}
