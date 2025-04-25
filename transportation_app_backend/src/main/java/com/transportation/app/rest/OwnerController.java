package com.transportation.app.rest;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.binding.DriverParameter;
import com.transportation.app.service.OwnerService;
import com.transportation.app.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Owner-related operations.
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final DriverService driverService;

    @Autowired
    public OwnerController(OwnerService ownerService, DriverService driverService) {
        this.ownerService = ownerService;
        this.driverService = driverService;
    }

    /**
     * Registers a new owner.
     *
     * @param ownerParameter the owner details
     * @return ResponseEntity with the result message
     */
    @PostMapping("/AddOwner")
    public ResponseEntity<String> registerOwner(@RequestBody OwnerParameter ownerParameter) {
        String result = ownerService.createOwner(ownerParameter);
        return ResponseEntity.status(201).body(result);
    }

    /**
     * Authenticates an owner.
     *
     * @param loginParamOwner the login parameters
     * @return ResponseEntity with the login response
     */
    @PostMapping("/loginOwner")
    public ResponseEntity<LoginResponseOwner> loginOwner(@RequestBody LoginParamOwner loginParamOwner) {
        LoginResponseOwner result = ownerService.checkLogin(loginParamOwner);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves driver details associated with a specific vehicle number.
     *
     * @param vehicleNumber the vehicle number
     * @return ResponseEntity with the list of drivers or an error message
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
     * Updates an existing owner's details.
     *
     * @param ownerId        the ID of the owner to update
     * @param ownerParameter the updated owner details
     * @return ResponseEntity with the result message
     */
    @PutMapping("/update/{ownerId}")
    public ResponseEntity<String> updateOwner(@PathVariable int ownerId, @RequestBody OwnerParameter ownerParameter) {
        ownerParameter.setId(ownerId);
        String result = ownerService.updateOwner(ownerParameter);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes an owner by ID.
     *
     * @param ownerId the ID of the owner to delete
     * @return ResponseEntity with the result message
     */
    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable int ownerId) {
        String result = ownerService.deleteOwner(ownerId);
        return ResponseEntity.ok(result);
    }
}
