package com.transportmanagementfrontend;


import java.util.List;

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

    @POST("login")
        // Adjust endpoint if needed
    Call<CustomerLoginResponse> loginUser(@Body CustomerLoginRequest loginRequest);

    @POST("loginOwner")
        // Adjust endpoint if needed
    Call<OwnerLoginResponse> loginOwner(@Body OwnerLoginRequest loginRequest);

    @POST("addUser")
        // Backend API for customer registration
    Call<String> registerUser(@Body CustomerRegisterRequest registerRequest);

    @POST("driver/AddUpdateDriver")
        // Driver registration
    Call<String> registerDriver(@Body DriverRegisterRequest registerRequest);

    @POST("loginDriver")
    Call<DriverLoginResponse> loginDriver(@Body DriverLoginRequest request);

    @POST("AddUpdateOwner")
    Call<String> registerOwner(@Body OwnerRegisterRequest registerRequest);

    @PUT("owners/username/{username}")
    Call<String> updateOwnerByUsername(
            @Path("username") String username,
            @Body OwnerRegisterRequest request
    );
    @GET("owners/username/{username}")
    Call<OwnerParameter> getOwnerByUsername(@Path("username") String username);

    @POST("createOwner")
        // Your backend endpoint for full owner creation
    Call<String> createOwner(@Body OwnerParameter owner);

    @GET("FindOwner/{id}")
    Call<OwnerParameter> getOwnerById(@Path("id") int id);

    // Get all payment records (dummy or real)
    @GET("payments/get_all")
    Call<List<PaymentParameter>> getAllPayments();

    // Get vehicle orders
    @GET("orders") // Endpoint: /api/orders
    Call<List<VehicleOrderParameter>> getVehicleOrders();

    // Endpoint to send a verification code to the user's email
    @POST("user/forgot-password")
    @FormUrlEncoded
    Call<String> sendVerificationCode(@Field("email") String email);

    // Endpoint to verify the code sent to the user's email
    @POST("user/verify-code")
    @FormUrlEncoded
    Call<String> verifyCode(@Field("email") String email, @Field("code") String code);

    // Fetch driver details by vehicle number (Updated)
    @GET("api/owners/drivers/{vehicleNumber}")
    Call<List<DriverParameter>> getDriverByVehicle(@Path("vehicleNumber") String vehicleNumber);

    @GET("api/admin/users")
    Call<List<UserModel>> getAllUsers();

    @PUT("api/admin/users/approve/{userId}")
    Call<Void> approveUser(@Path("userId") Long userId);

    @GET("api/admin/profile")
    Call<AdminRequest> getAdminProfile();

    @PUT("api/admin/profile/update")
    Call<Void> updateAdminProfile(@Body AdminRequest admin);

    @POST("api/admin/subadmin")
    Call<Void> createSubAdmin(@Body AdminRequest subadmin);

    @GET("api/admin/backup")
    Call<Void> backupSystem();

    @GET("api/admin/complaints")
    Call<List<Complaint>> getAllComplaints();
    @GET("api/admin/vehicles")
    Call<List<Vehicle>> getAllVehicles();
}
