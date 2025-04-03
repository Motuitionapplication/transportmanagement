package com.transportation.app.repo;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportation.app.binding.DriverParameter;

public interface DriverRepository extends JpaRepository<DriverParameter, Serializable> {
    DriverParameter findByPassword(String password);
    DriverParameter findByUsername(String username);
    
    // Method to fetch drivers by vehicleNumber
    List<DriverParameter> findByVehicleNumber(String vehicleNumber);
}
