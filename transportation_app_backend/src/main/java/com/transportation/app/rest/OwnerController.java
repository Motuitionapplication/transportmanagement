package com.transportation.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.service.DriverService;
import com.transportation.app.service.OwnerService;

/**
 * REST controller for managing Owner-related operations.
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private final OwnerService ownerService;

    private final DriverService driverService;

    // Constructor injection
    public OwnerController(OwnerService ownerService, DriverService driverService) {
        this.ownerService = ownerService;
        this.driverService = driverService;
    }

    /**
     * Endpoint to create or update an owner. Used for both insert and update operations.
     *
     * @param ownerParameter the owner data
     * @return response indicating creation or update status
     */
    @PostMapping("/AddUpdateOwner")
    public ResponseEntity<String> registerOwner(@RequestBody OwnerParameter ownerParameter) {
        String result = ownerService.createOrUpdateOwner(ownerParameter);
        return ResponseEntity.status(201).body(result);
    }

    /**
     * Fetches a single owner by ID.
     *
     * @param id the owner's ID
     * @return owner object if found, 404 otherwise
     */
    @GetMapping("/FindOwner/{id}")
    public ResponseEntity<OwnerParameter> getOwnerById(@PathVariable int id) {
        OwnerParameter owner = ownerService.getOwnerById(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    /**
     * Login endpoint for owners.
     *
     * @param loginParamOwner the login credentials
     * @return login response including success status and message
     */
    @PostMapping("/loginOwner")
    public ResponseEntity<LoginResponseOwner> loginOwner(@RequestBody LoginParamOwner loginParamOwner) {
        LoginResponseOwner result = ownerService.checkLogin(loginParamOwner);
        return ResponseEntity.ok(result);
    }

    /**
     * Fetches drivers based on vehicle number.
     *
     * @param vehicleNumber the vehicle number to search drivers for
     * @return list of drivers or bad request if input is missing
     */
    @GetMapping("/drivers/{vehicleNumber}")
    public ResponseEntity<?> getDriverDetailsByVehicleNumber(@PathVariable String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle number is required");
        }
        List<DriverParameter> drivers = driverService.getDriversByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(drivers);
    }

    /**
     * Updates an existing owner's details or creates a new one if not found.
     *
     * @param ownerId ID to update
     * @param ownerParameter owner data
     * @return status message
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> upsertOwner(@PathVariable int ownerId, @RequestBody OwnerParameter ownerParameter) {
        ownerParameter.setId(ownerId);
        String result = ownerService.updateOwner(ownerParameter);

        if ("Owner not found".equalsIgnoreCase(result) || "Owner with ID not found".equalsIgnoreCase(result)) {
            String createResult = ownerService.createOrUpdateOwner(ownerParameter);
            return ResponseEntity.status(201).body("Owner not found, so a new owner was created. " + createResult);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Deletes an owner by ID.
     *
     * @param id the ID to delete
     * @return deletion result
     */
    @DeleteMapping("/Owner/delete/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable int id) {
        String result = ownerService.deleteOwner(id);
        return ResponseEntity.ok(result);
    }
} 
