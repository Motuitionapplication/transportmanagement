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
 * REST controller for managing Owner-related operations.vb
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
// For update and add same time
    @PostMapping("/AddUpdateOwner")
    public ResponseEntity<String> registerOwner(@RequestBody OwnerParameter ownerParameter) {
        String result = ownerService.createOrUpdateOwner(ownerParameter);
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/loginOwner")
    public ResponseEntity<LoginResponseOwner> loginOwner(@RequestBody LoginParamOwner loginParamOwner) {
        LoginResponseOwner result = ownerService.checkLogin(loginParamOwner);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/drivers/{vehicleNumber}")
    public ResponseEntity<?> getDriverDetailsByVehicleNumber(@PathVariable String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle number is required");
        }
        List<DriverParameter> drivers = driverService.getDriversByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(drivers);
    }

    /**
     * Updates an existing owner's details or creates a new owner if the ID does not exist.
     *
     * @param ownerId        the ID of the owner to update or create
     * @param ownerParameter the updated or new owner details
     * @return ResponseEntity with the result message
     */
    @PutMapping("/update/{ownerId}")
    public ResponseEntity<String> upsertOwner(@PathVariable int ownerId, @RequestBody OwnerParameter ownerParameter) {
        ownerParameter.setId(ownerId);
        String result = ownerService.updateOwner(ownerParameter);

        if ("Owner not found".equalsIgnoreCase(result) || "Owner with ID not found".equalsIgnoreCase(result)) {
            // If update failed because the owner wasn't found, create new owner with given ID
            String createResult = ownerService.createOrUpdateOwner(ownerParameter);
            return ResponseEntity.status(201).body("Owner not found, so a new owner was created. " + createResult);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable int ownerId) {
        String result = ownerService.deleteOwner(ownerId);
        return ResponseEntity.ok(result);
    }
}
