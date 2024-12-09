package com.example.expensemanagement.sqlite_database.config.api;

import com.example.expensemanagement.sqlite_database.dto.request.RequireEmailRequest;
import com.example.expensemanagement.sqlite_database.dto.response.RequireOtpResponse;
import com.example.expensemanagement.sqlite_database.dto.request.VerifyEmailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OTPApi {
    @POST("otp/request")
    Call<RequireOtpResponse> requestOTP(@Body RequireEmailRequest requireEmailRequest);

    @POST("otp/verify")
    Call<RequireOtpResponse> verifyOTP(@Body VerifyEmailRequest verifyEmailRequest);
}
