package com.transportation.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.DriverParameter;

public interface DriverRepository extends JpaRepository<DriverParameter, Integer> {
    
    DriverParameter findByPassword(String password);
    
    Optional<DriverParameter> findByUsername(String username);
    
    // Fetch drivers by vehicleNumber
    List<DriverParameter> findByVehicleNumber(String vehicleNumber);
    
    List<DriverParameter> findByCityAndVehicleNumberAndVehicleType(String city, String vehicleNumber, String vehicleType);

}