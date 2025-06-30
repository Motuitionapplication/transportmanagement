package com.transportation.app.service;

import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.UserParameter;

public interface UserService {

    String createUser(UserParameter userParameter);

    LoginResponse checkLogin(LoginParam loginParam);
    
    String generateOTP(String phone);
    
    String forgotPassword(String email);
}
