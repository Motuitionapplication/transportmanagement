package com.transportmanagementfrontend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login") // Adjust endpoint if needed
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("addUser") //  backend API
    Call<String> registerUser(@Body RegisterRequest registerRequest);
}
