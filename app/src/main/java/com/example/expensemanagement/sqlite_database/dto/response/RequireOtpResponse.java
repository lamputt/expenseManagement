package com.example.expensemanagement.sqlite_database.dto.response;

import com.example.expensemanagement.sqlite_database.dto.BaseResponse;

public class RequireOtpResponse extends BaseResponse {

    private Data data;

    @Override
    public Data getData() {
        return data;
    }

    public static class Data{
        private String transId;
        private String otp;

        public String getTransId() {
            return transId;
        }

        public String getOtp() {
            return otp;
        }
    }
}
