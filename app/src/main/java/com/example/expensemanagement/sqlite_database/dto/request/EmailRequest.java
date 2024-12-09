package com.example.expensemanagement.sqlite_database.dto.request;

public class EmailRequest {

    private String email;
    private String password;

    public String setEmail(){
        return email;
    }

    public String setPassword(){
        return password;
    }

    public EmailRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
