package com.transportation.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.service.DriverServiceImpl;

import jakarta.validation.Valid;

/**
 * REST controller for managing Driver-related operations.
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    private DriverServiceImpl driverService;

    /**
     * Creates a new Driver and links it with an Owner via username.
     *
     * @param driverParameter the driver details including owner.username
     * @return a response message indicating success or failure
     */
    @PostMapping("/driver/AddUpdateDriver")
    public ResponseEntity<String> addOrUpdateDriver(@RequestBody @Valid DriverParameter driverParameter) {
        if (driverParameter.getOwner() == null || driverParameter.getOwner().getUsername() == null) {
            return ResponseEntity.badRequest().body("Owner username must be provided.");
        }

        String message = driverService.createOrUpdateDriver(driverParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }



    /**
     * Retrieves a Driver by ID.
     *
     * @param id the driver's ID
     * @return the driver details or 404 if not found
     */
    @GetMapping("driver/FindDriver/{id}")
    public ResponseEntity<DriverParameter> getDriverById(@PathVariable int id) {
        DriverParameter driver = driverService.getDriverById(id);
        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(driver);
    }

    /**
     * Authenticates a driver using username and password.
     *
     * @param loginParamDriver the login credentials
     * @return login status and driver details if valid
     */
    @PostMapping("driver/loginDriver")
    public ResponseEntity<LoginResponseDriver> loginDriver(@RequestBody LoginParamDriver loginParamDriver) {
        LoginResponseDriver result = driverService.checkLogin(loginParamDriver);
        return ResponseEntity.ok(result);
    }

    /**
     * Updates an existing driver using ID from path.
     *
     * @param id              the ID of the driver to update
     * @param driverParameter the updated driver details
     * @return a response message indicating success or failure
     */
//    @PutMapping("/driver/update/{id}")
//    public ResponseEntity<String> updateDriver(@PathVariable int id, @RequestBody DriverParameter driverParameter) {
//        driverParameter.setId(id);
//        String result = driverService.updateDriver(driverParameter);
//        return ResponseEntity.ok(result);
//    }

    /**
     * Deletes a driver by ID.
     *
     * @param id the ID of the driver to delete
     * @return a response message indicating deletion status
     */
    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable int id) {
        String result = driverService.deleteDriver(id);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/driver/filter")
    public ResponseEntity<List<DriverParameter>> filterDrivers(@RequestBody DriverParameter filter) {
        String city = filter.getCity();
        String vehicleNumber = filter.getVehicleNumber();
        String vehicleType = filter.getVehicleType();

        if (city == null || vehicleNumber == null || vehicleType == null) {
            return ResponseEntity.badRequest().build();
        }

        List<DriverParameter> drivers = driverService.filterDrivers(city, vehicleNumber, vehicleType);

        if (drivers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(drivers);
    }

    
}

