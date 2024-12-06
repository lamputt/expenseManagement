package com.example.expensemanagement.Model;

public class ItemDateTransaction {
    private String Date ;
    private Double Price;
    private String Type;

    public ItemDateTransaction (String date , Double price , String Type) {
        this.Date  = date;
        this.Price = price;
        this.Type = Type;
    }

    public String getDate() {
        return this.Date;
    }

    public Double getPrice () {
        return this.Price;
    }

    public String getType () {
        return this.Type;
    }

}
