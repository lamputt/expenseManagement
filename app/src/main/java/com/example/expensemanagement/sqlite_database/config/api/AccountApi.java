package com.example.expensemanagement.sqlite_database.config.api;

import com.example.expensemanagement.sqlite_database.dto.request.UpdateAccountRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface AccountApi {
    @PUT("account/update")
    Call<Void> updateAccount(@Body UpdateAccountRequest request, @Header("Authorization") String token);
}
