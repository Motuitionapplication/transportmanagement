package com.transportmanagementfrontend;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login") // Adjust endpoint if needed
    Call<CustomerLoginResponse> loginUser(@Body CustomerLoginRequest loginRequest);

    @POST("loginOwner") // Adjust endpoint if needed
    Call<OwnerLoginResponse> loginOwner(@Body OwnerLoginRequest loginRequest);

    @POST("addUser") // Backend API for customer registration
    Call<String> registerUser(@Body CustomerRegisterRequest registerRequest);

    @POST("addDriver") // Driver registration
    Call<String> registerDriver(@Body DriverRegisterRequest registerRequest);

    @POST("Driverlogin")
    Call<DriverLoginResponse> loginDriver(@Body DriverLoginRequest request);

    @POST("AddUpdateOwner")
    Call<String> registerOwner(@Body OwnerRegisterRequest registerRequest);

    @PUT("update/{ownerId}")
    Call<String> updateOwner(@Path("ownerId") int ownerId, @Body OwnerRegisterRequest request);

    @POST("createOwner") // Your backend endpoint for full owner creation
    Call<String> createOwner(@Body OwnerParameter owner);

    @GET("FindOwner/{id}")
    Call<OwnerParameter> getOwnerById(@Path("id") int id);

    // Endpoint to send a verification code to the user's email
    @POST("user/forgot-password")
    @FormUrlEncoded
    Call<String> sendVerificationCode(@Field("email") String email);

    // Endpoint to verify the code sent to the user's email
    @POST("user/verify-code")
    @FormUrlEncoded
    Call<String> verifyCode(@Field("email") String email, @Field("code") String code);

    // Fetch driver details by vehicle number (Updated)
    @GET("driverDetails")
    Call<DriverParameter> getDriverByVehicle(@Query("vehicleNumber") String vehicleNumber);
}
