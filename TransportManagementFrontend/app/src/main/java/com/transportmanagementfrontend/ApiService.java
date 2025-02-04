package com.transportmanagementfrontend;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login") // Adjust the endpoint based on your backend
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
