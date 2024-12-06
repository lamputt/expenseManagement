package api;

import com.example.expensemanagement.activity.ForgotPasswordRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/request")
    Call<String> sendOtp(@Body ForgotPasswordRequest request);
}
