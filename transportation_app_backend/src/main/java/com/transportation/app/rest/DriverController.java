package com.transportation.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.service.DriverService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class DriverController {
    
    @Autowired
    private DriverService driverService;
    
    @PostMapping("/addDriver")
    public ResponseEntity<String> registerDriver(@RequestBody DriverParameter driverParameter) {
        String result = driverService.createDriver(driverParameter);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PostMapping("/Driverlogin")
    public ResponseEntity<LoginResponseDriver> loginDriver(@RequestBody LoginParamDriver loginParamDriver) {
        LoginResponseDriver result = driverService.checkLogin(loginParamDriver);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Endpoint: Update Driver by ID (ID provided in path)
    @PutMapping("/driver/update/{driverId}")
    public ResponseEntity<String> updateDriver(@PathVariable("driverId") int driverId, @RequestBody DriverParameter driverParameter) {
        driverParameter.setId(driverId);
        String result = driverService.updateDriver(driverParameter);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Endpoint: Delete Driver by ID
    @DeleteMapping("/driver/delete/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable("driverId") int driverId) {
        String result = driverService.deleteDriver(driverId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
