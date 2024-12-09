package com.example.expensemanagement.sqlite_database.controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.expensemanagement.sqlite_database.config.ConfigApi;
import com.example.expensemanagement.sqlite_database.config.api.AccountApi;
import com.example.expensemanagement.sqlite_database.dto.request.UpdateAccountRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountController {
    private final Context context;

    public AccountController(Context context) {
        super();
        this.context = context;
    }

    public void updateAccount(UpdateAccountRequest updateAccountRequest){
        AccountApi accountApi = ConfigApi.getRetrofitInstance().create(AccountApi.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("AuthenticationPrefs", Context.MODE_PRIVATE);
        String Token = sharedPreferences.getString("token", null);

        Call<Void> call = accountApi.updateAccount(updateAccountRequest, Token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    // Xử lý dữ liệu trả về
                    Log.d(TAG, "Update Successfully");
                }else {
                    Log.d(TAG, "Update Failed" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}
