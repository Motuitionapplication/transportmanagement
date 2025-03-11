package com.transportation.app.service;

import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.UserParameter;

public interface UserService {

    String createUser(UserParameter userParameter);

    LoginResponse checkLogin(LoginParam loginParam);
    
    String generateOTP(String phone);
    
    // Forgot password now accepts only an email address
    String forgotPassword(String email);
}
