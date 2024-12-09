package com.example.expensemanagement.sqlite_database.dto.response;

import com.example.expensemanagement.sqlite_database.dto.BaseResponse;

public class AuthenticationResponse extends BaseResponse {
    private Data data;

    @Override
    public Data getData() {
        return data;
    }

    public static class Data{
        private String token;
        private String refreshToken;

        //setter and getter
        public String getToken(){
            return token;
        }

        public String getRefreshToken(){
            return refreshToken;
        }
    }
}
