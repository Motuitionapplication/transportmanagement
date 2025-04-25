package com.transportation.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
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

    @Override
    public List<DriverParameter> getDriversByVehicleNumber(String vehicleNumber) {
        return driverRepo.findByVehicleNumber(vehicleNumber);
    }

    // New method: Update Driver by ID
    @Override
    public String updateDriver(DriverParameter driverParameter) {
        if(driverParameter.getId() == 0) {
            return "Driver ID is required for update";
        }
        Optional<DriverParameter> optionalDriver = driverRepo.findById(driverParameter.getId());
        if (!optionalDriver.isPresent()) {
            return "Driver not found";
        }
        DriverParameter existingDriver = optionalDriver.get();
        // Update fields – adjust as needed
        existingDriver.setFirstName(driverParameter.getFirstName());
        existingDriver.setLastName(driverParameter.getLastName());
        existingDriver.setPhone(driverParameter.getPhone());
        existingDriver.setUsername(driverParameter.getUsername());
        existingDriver.setPassword(driverParameter.getPassword());
        existingDriver.setDlNumber(driverParameter.getDlNumber());
        existingDriver.setVehicleNumber(driverParameter.getVehicleNumber());
        existingDriver.setSelectedRole(driverParameter.getSelectedRole());
        existingDriver.setEmail(driverParameter.getEmail());
        driverRepo.save(existingDriver);
        return "Driver updated successfully";
    }

    // New method: Delete Driver by ID
    @Override
    public String deleteDriver(int driverId) {
        if(driverRepo.existsById(driverId)) {
            driverRepo.deleteById(driverId);
            return "Driver deleted successfully";
        } else {
            return "Driver not found";
        }
    }
}
