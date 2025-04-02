package com.transportmanagementfrontend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login") // Adjust endpoint if needed
    Call<CustomerLoginResponse> loginUser(@Body CustomerLoginRequest loginRequest);

    @POST("addUser") //  backend API for customer registration
    Call<String> registerUser(@Body CustomerRegisterRequest registerRequest);

    @POST("addDriver") // Driver registration
    Call<String> registerDriver(@Body DriverRegisterRequest registerRequest);

    @POST("addOwner") // Owner registration
    Call<String> registerOwner(@Body OwnerRegisterRequest registerRequest);

    // Endpoint to send a verification code to the user's email
    @POST("user/forgot-password")
    @FormUrlEncoded
    Call<String> sendVerificationCode(@Field("email") String email);

    // Endpoint to verify the code sent to the user's email
    @POST("user/verify-code")
    @FormUrlEncoded
    Call<String> verifyCode(@Field("email") String email, @Field("code") String code);
}
