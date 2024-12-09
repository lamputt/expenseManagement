package com.example.expensemanagement.sqlite_database.dto.request;

public class RegisterRequest {
    private final String email;
    private final String userName;
    private final String password;
    private final String transId;

    public RegisterRequest(String transId, String email, String userName, String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.transId = transId;
    }

    public String getTransId() {
        return transId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
