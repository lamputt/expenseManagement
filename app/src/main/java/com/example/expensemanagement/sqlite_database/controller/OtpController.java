package com.example.expensemanagement.sqlite_database.controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.expensemanagement.sqlite_database.config.ConfigApi;
import com.example.expensemanagement.sqlite_database.config.api.OTPApi;
import com.example.expensemanagement.sqlite_database.dto.request.RequireEmailRequest;
import com.example.expensemanagement.sqlite_database.dto.response.RequireOtpResponse;
import com.example.expensemanagement.sqlite_database.dto.request.VerifyEmailRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpController {
    private final Context context;

    public OtpController(Context context) {
        super();
        this.context = context;
    }

    public void sendOtp(String email) {
        // Gửi yêu cầu API để gửi OTP đến email
        OTPApi otpApi = ConfigApi.getRetrofitInstance().create(OTPApi.class);

        // Gửi yêu cầu API
        Call<RequireOtpResponse> call = otpApi.requestOTP(new RequireEmailRequest(email));
        call.enqueue(new Callback<RequireOtpResponse>() {
            @Override
            public void onResponse(Call<RequireOtpResponse> call, Response<RequireOtpResponse> response) {
                if(response.code() == 200){
                    RequireOtpResponse authenticationResponse = response.body();
                    if (authenticationResponse != null) {
                        RequireOtpResponse.Data data = authenticationResponse.getData();

                        SharedPreferences sharedPreferences = context.getSharedPreferences("OtpPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("transId", data.getTransId());
                        editor.putString("otp", data.getOtp());
                        editor.apply();
                    }
                    Log.d(TAG, "send otp Successfully");
                }else {
                    Log.d(TAG, "send otp Failed");
                }
            }

            @Override
            public void onFailure(Call<RequireOtpResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });

    }

    public void verifyOtp(String otp) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("OtpPrefs", Context.MODE_PRIVATE);
        String transId = sharedPreferences.getString("transId", null);

        // Gửi yêu cầu API để xác minh OTP
        OTPApi otpApi = ConfigApi.getRetrofitInstance().create(OTPApi.class);

        Call<RequireOtpResponse> call = otpApi.verifyOTP(new VerifyEmailRequest(otp, transId));
        call.enqueue(new Callback<RequireOtpResponse>() {
            @Override
            public void onResponse(Call<RequireOtpResponse> call, Response<RequireOtpResponse> response) {
                if (response.code() == 200) {
                    RequireOtpResponse authenticationResponse = response.body();
                    if (authenticationResponse != null) {
                        Log.d(TAG, "OTP verified successfully");
                    }
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("transId");
                    editor.apply();
                    Log.d(TAG, "OTP verification failed");
                }
            }

            @Override
            public void onFailure(Call<RequireOtpResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}