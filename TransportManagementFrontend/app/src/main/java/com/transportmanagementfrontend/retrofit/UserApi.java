package com.transportmanagementfrontend.retrofit;

import com.transportmanagementfrontend.model.UserParameter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/addUser")
    Call<UserParameter> save(@Body UserParameter userParameter);

}
