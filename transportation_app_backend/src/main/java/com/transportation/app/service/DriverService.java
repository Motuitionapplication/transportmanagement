package com.transportation.app.service;

import java.util.List;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.binding.OwnerParameter;

public interface DriverService {

	
    public String createOrUpdateDriver(DriverParameter driverParameter);
    
    public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver);
    
	public DriverParameter findByUsername(String username);
    
    // Retrieve drivers by vehicleNumber
   public  List<DriverParameter> getDriversByVehicleNumber(String vehicleNumber);
    
    // Update and delete operations
   public String updateDriver(DriverParameter driverParameter);
   
   
   public  String deleteDriver(int id);
   
   
   public DriverParameter getDriverById(int id);
}
