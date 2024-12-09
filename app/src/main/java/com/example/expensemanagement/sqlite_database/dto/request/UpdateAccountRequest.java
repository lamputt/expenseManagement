package com.example.expensemanagement.sqlite_database.dto.request;

public class UpdateAccountRequest {
    private String userName;
    private String email;
    private String password;

    public UpdateAccountRequest(String userName, String email, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public UpdateAccountRequest(String password){
        this.password = password;
    }
}
