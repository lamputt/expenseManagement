package com.example.expensemanagement.sqlite_database.controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.expensemanagement.sqlite_database.config.ConfigApi;
import com.example.expensemanagement.sqlite_database.config.api.AuthenticationApi;
import com.example.expensemanagement.sqlite_database.dto.request.EmailRequest;
import com.example.expensemanagement.sqlite_database.dto.response.AuthenticationResponse;
import com.example.expensemanagement.sqlite_database.dto.request.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationController {
    private final Context context;

    public AuthenticationController(Context context) {
        super();
        this.context = context;
    }

    public void register(RegisterRequest rq) {
        AuthenticationApi authenticationApi = ConfigApi.getRetrofitInstance().create(AuthenticationApi.class);

        // Gửi yêu cầu API
        Call<AuthenticationResponse> call = authenticationApi.register(rq);
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if(response.isSuccessful()){
                    AuthenticationResponse authenticationResponse = response.body();
                    if (authenticationResponse != null) {
                        AuthenticationResponse.Data data = authenticationResponse.getData();
                        // Xử lý dữ liệu trả về
                        SharedPreferences sharedPreferences = context.getSharedPreferences("AuthenticationPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", data.getToken());
                        editor.putString("refreshToken", data.getRefreshToken());
                        editor.apply();
                    }
                    Log.d(TAG, "Register Successfully");
                }else {
                    Log.d(TAG, "Register Failed"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }

    public void authenticate(String email, String hashedPassword) {
        AuthenticationApi authenticationApi = ConfigApi.getRetrofitInstance().create(AuthenticationApi.class);

        //send api
        Call<AuthenticationResponse> call = authenticationApi.authenticate(new EmailRequest(email, hashedPassword));
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if(response.isSuccessful()){
                    AuthenticationResponse authenticationResponse = response.body();
                    if (authenticationResponse != null) {
                        AuthenticationResponse.Data data = authenticationResponse.getData();
                        // Xử lý dữ liệu trả về
                        SharedPreferences sharedPreferences = context.getSharedPreferences("AuthenticationPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", data.getToken());
                        editor.putString("refreshToken", data.getRefreshToken());
                        editor.apply();
                    }
                    Log.d(TAG, "Register Successfully");
                }else {
                    Log.d(TAG, "Register Failed"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}
