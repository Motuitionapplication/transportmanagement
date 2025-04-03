package com.transportation.app.service;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;

public interface OwnerService {

    String createOwner(OwnerParameter ownerParameter);
    LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner);
    
    // Method to retrieve owner details by username (if needed elsewhere)
    OwnerParameter findByUsername(String username);
}
