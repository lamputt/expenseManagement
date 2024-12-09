package com.example.expensemanagement.sqlite_database.dto;

public abstract class BaseResponse {
    private String code;
    private int status;
    private String message;

    // Getter và Setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract Object getData();
}
