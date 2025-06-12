package com.transportation.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.repo.DriverRepository;
import com.transportation.app.repo.OwnerRepository;

@Service
public class DriverServiceImpl {

    private final DriverRepository driverRepo;
    private final OwnerRepository  ownerRepo;

    public DriverServiceImpl(DriverRepository driverRepo,
                             OwnerRepository ownerRepo) {
        this.driverRepo = driverRepo;
        this.ownerRepo  = ownerRepo;
    }

    /* --------------------------------------------------
     * CREATE or UPDATE
     * -------------------------------------------------- */
  
    @Transactional
    public String createOrUpdateDriver(DriverParameter driverParameter) {
        if (driverParameter.getOwner() == null || driverParameter.getOwner().getUsername() == null) {
            throw new IllegalArgumentException("Owner username must be provided.");
        }

        String ownerUsername = driverParameter.getOwner().getUsername();

        // Fetch existing owner
        OwnerParameter owner = ownerRepo.findByUsername(ownerUsername)
            .orElseThrow(() -> new RuntimeException("Owner not found with username: " + ownerUsername));

        // Set the full owner entity to the driver
        driverParameter.setOwner(owner);

        driverRepo.save(driverParameter);

        return "Driver updated successfully";
    }


     
    /* --------------------------------------------------
     * AUTHENTICATION
     * -------------------------------------------------- */
   
    public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver) {
        LoginResponseDriver resp = new LoginResponseDriver();

        Optional<DriverParameter> driverOpt = driverRepo.findByUsername(loginParamDriver.getUsername());

        if (driverOpt.isPresent()) {
            DriverParameter driver = driverOpt.get();
            if (driver.getPassword().equals(loginParamDriver.getPassword())) {
                resp.setSuccess(true);
                resp.setStatus("Success");
                resp.setDriver(Optional.of(driver));
                return resp;
            }
        }

        resp.setSuccess(false);
        resp.setStatus("Invalid Username or Password");
        return resp;
    }


    /* --------------------------------------------------
     * QUERY helpers
     * -------------------------------------------------- */
    
    public List<DriverParameter> getDriversByVehicleNumber(String vehicleNumber) {
        return driverRepo.findByVehicleNumber(vehicleNumber);
    }

    
    public DriverParameter getDriverById(int id) {
        return driverRepo.findById(id).orElse(null);
    }

    
    public DriverParameter findByUsername(String username) {
        return driverRepo.findByUsername(username)
                .orElse(null);  // or throw exception if preferred
    }

    /* --------------------------------------------------
     * UPDATE (via controller) – PUT /driver/update/{id}
     * -------------------------------------------------- */
    
//    @Transactional
//    public String updateDriver(DriverParameter driverParameter) {
//
//        if (driverParameter.getId() == null) {
//            return "Driver ID is required for update";
//        }
//
//        DriverParameter existing = driverRepo
//                .findById(driverParameter.getId())
//                .orElse(null);
//
//        if (existing == null) {
//            return "Driver not found";
//        }
//
//        /* FK update (optional) */
//        if (driverParameter.getOwner() != null &&
//            driverParameter.getOwner().getUsername() != null) {
//
//            OwnerParameter owner = ownerRepo
//                    .findByUsername(driverParameter.getOwner().getUsername())
//                    .orElseThrow(() ->
//                         new IllegalArgumentException(
//                             "Owner not found with username: "
//                             + driverParameter.getOwner().getUsername()));
//            existing.setOwner(owner);
//        }
//
//        copyFields(driverParameter, existing);
//        driverRepo.save(existing);
//        return "Driver updated successfully";
//    }

    /* --------------------------------------------------
     * DELETE
     * -------------------------------------------------- */
 
    public String deleteDriver(int id) {
        if (driverRepo.existsById(id)) {
            driverRepo.deleteById(id);
            return "Driver deleted successfully";
        }
        return "Driver not found";
    }

    /* --------------------------------------------------
     * INTERNAL HELPER
     * -------------------------------------------------- */
    private static void copyFields(DriverParameter src, DriverParameter dest) {
        dest.setFirstName(src.getFirstName());
        dest.setLastName(src.getLastName());
        dest.setPhone(src.getPhone());
        dest.setUsername(src.getUsername());
        dest.setPassword(src.getPassword());
        dest.setDlNumber(src.getDlNumber());
        dest.setDlType(src.getDlType());
        dest.setEmail(src.getEmail());
        dest.setFatherOrHusbandName(src.getFatherOrHusbandName());
        dest.setPassportPhoto(src.getPassportPhoto());
        dest.setIdentityProofType(src.getIdentityProofType());
        dest.setIdentityProofFilePath(src.getIdentityProofFilePath());
        dest.setInsurancePaperFilePath(src.getInsurancePaperFilePath());
        dest.setBloodGroup(src.getBloodGroup());
        dest.setVehicleNumber(src.getVehicleNumber());
        dest.setRole(src.getRole());
    }
    
    public List<DriverParameter> filterDrivers(String city, String vehicleNumber, String vehicleType) {
        return driverRepo.findByCityAndVehicleNumberAndVehicleType(city, vehicleNumber, vehicleType);
    }

}
