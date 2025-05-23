package com.transportation.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.service.DriverService;

/**
 * REST controller for managing Driver-related operations.
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * Creates or updates a Driver.
     *
     * @param driverParameter the driver details
     * @return a response message indicating creation or update status
     */
    @PostMapping("/driver/AddUpdateDriver")
    public ResponseEntity<String> registerDriver(@RequestBody DriverParameter driverParameter) {
        String result = driverService.createOrUpdateDriver(driverParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
    @PutMapping("/driver/update/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable int id, @RequestBody DriverParameter driverParameter) {
        driverParameter.setId(id);
        String result = driverService.updateDriver(driverParameter);
        return ResponseEntity.ok(result);
    }

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
}
