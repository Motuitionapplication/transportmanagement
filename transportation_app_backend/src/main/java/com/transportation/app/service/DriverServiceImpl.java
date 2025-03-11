package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.binding.UserParameter;
import com.transportation.app.repo.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverRepository driverRepo;

	public DriverServiceImpl(DriverRepository driverRepo) {
		this.driverRepo = driverRepo;
	}

	@Override
	public String createDriver(DriverParameter driverParameter) {
		driverRepo.save(driverParameter);
		return "Driver created";
	}

	@Override
	public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver) {
		 LoginResponseDriver response = new LoginResponseDriver();
	        Optional<DriverParameter> driver = Optional.empty();
	        try {
	            driver = Optional.ofNullable(driverRepo.findByUsername(loginParamDriver.getUsername()));
	            if (driver.isPresent() && driver.get().getPassword().equals(loginParamDriver.getPassword())) {
	                response.setStatus("Success");
	                response.setSuccess(true);
	                response.setDriver(driver);
	                return response;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        response.setStatus("Invalid User Name & Password");
	        return response;
	}
}
