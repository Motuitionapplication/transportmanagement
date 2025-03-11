package com.transportation.app.service;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.LoginResponseDriver;

public interface DriverService {
	
    String createDriver(DriverParameter driverParameter);
    
    LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver);

}
