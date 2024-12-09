package com.example.expensemanagement.sqlite_database.config.api;

import com.example.expensemanagement.sqlite_database.dto.response.AuthenticationResponse;
import com.example.expensemanagement.sqlite_database.dto.request.EmailRequest;
import com.example.expensemanagement.sqlite_database.dto.request.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    @POST("authentication")
    Call<AuthenticationResponse> authenticate(@Body EmailRequest request);

    @POST("authentication/register")
    Call<AuthenticationResponse> register(@Body RegisterRequest request);
}
