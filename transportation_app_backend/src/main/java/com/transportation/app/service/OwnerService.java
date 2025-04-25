package com.transportation.app.service;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;

public interface OwnerService {

    String createOwner(OwnerParameter ownerParameter);
    LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner);
    
    // Retrieve owner details by username
    OwnerParameter findByUsername(String username);
    
    // Update and delete operations
    String updateOwner(OwnerParameter ownerParameter);
    String deleteOwner(int ownerId);
}
