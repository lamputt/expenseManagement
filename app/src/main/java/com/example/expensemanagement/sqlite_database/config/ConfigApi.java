package com.example.expensemanagement.sqlite_database.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/api/users/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS) // Thời gian chờ kết nối
                    .readTimeout(120, TimeUnit.SECONDS)    // Thời gian chờ đọc dữ liệu
                    .writeTimeout(120, TimeUnit.SECONDS)   // Thời gian chờ ghi dữ liệu
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
