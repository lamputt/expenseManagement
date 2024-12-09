package com.example.expensemanagement.sqlite_database.dto.request;

public class VerifyEmailRequest {
    private String otp;
    private String transId;

    // Getters and setters
    public VerifyEmailRequest(String otp, String transId) {
        this.otp = otp;
        this.transId = transId;
    }
}
