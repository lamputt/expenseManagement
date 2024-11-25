package com.example.expensemanagement.Model;

public class BankAccount {

    private final String name ;
    private String acountNumber ;
    private final float money;

    public BankAccount( String name , String acountNumber , float money) {
        this.acountNumber = acountNumber;
        this.name = name;
        this.money = money;
    }

    public String getAcountNumber() {
        return acountNumber;
    }

    public String getName() {
        return name;
    }

    public float getMoney () {
        return money;
    }
}
