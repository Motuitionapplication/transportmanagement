package com.transportation.app.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.service.OwnerService;
import com.transportation.app.service.DriverService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private DriverService driverService;
    
    @PostMapping("/addOwner")
    public ResponseEntity<String> registerOwner(@RequestBody OwnerParameter ownerParameter) {
        String result = ownerService.createOwner(ownerParameter);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PostMapping("/Ownerlogin")
    public ResponseEntity<LoginResponseOwner> loginOwner(@RequestBody LoginParamOwner loginParamOwner) {
        LoginResponseOwner result = ownerService.checkLogin(loginParamOwner);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Updated endpoint: Fetch driver details by vehicleNumber directly
    @GetMapping("/owner/driverDetails/{vehicleNumber}")
    public ResponseEntity<?> getDriverDetailsByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            return new ResponseEntity<>("Vehicle number is required", HttpStatus.BAD_REQUEST);
        }
        List<DriverParameter> drivers = driverService.getDriversByVehicleNumber(vehicleNumber);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }
}
