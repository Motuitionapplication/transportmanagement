package com.transportation.app.service;

import java.util.List;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;

public interface DriverService {

    String createDriver(DriverParameter driverParameter);
    LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver);
    
    // Retrieve drivers by vehicleNumber
    List<DriverParameter> getDriversByVehicleNumber(String vehicleNumber);
    
    // Update and delete operations
    String updateDriver(DriverParameter driverParameter);
    String deleteDriver(int driverId);
}
