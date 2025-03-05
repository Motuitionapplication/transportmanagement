package com.transportation.app.service;

import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.UserParameter;

public interface UserService {

    public String createUser(UserParameter userParameter);

    public LoginResponse checkLogin(LoginParam loginParam);
    
    // Updated to return String instead of void
    public String generateOTP(String phone);
}
