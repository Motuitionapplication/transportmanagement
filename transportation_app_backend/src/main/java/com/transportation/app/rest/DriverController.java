package com.transportation.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
}
