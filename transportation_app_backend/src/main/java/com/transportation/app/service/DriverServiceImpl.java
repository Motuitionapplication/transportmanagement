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
public class DriverServiceImpl implements DriverService {

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
    @Override
    @Transactional
    public String createOrUpdateDriver(DriverParameter driverParameter) {

        /* 1️⃣  Resolve the owner by username (FK) */
        if (driverParameter.getOwner() == null ||
            driverParameter.getOwner().getUsername() == null) {
            return "Owner username must be provided";
        }

        OwnerParameter owner = ownerRepo
                .findByUsername(driverParameter.getOwner().getUsername())
                .orElseThrow(() ->
                     new IllegalArgumentException(
                         "Owner not found with username: "
                         + driverParameter.getOwner().getUsername()));

        driverParameter.setOwner(owner);           // link FK

        /* 2️⃣  UPDATE path */
        if (driverParameter.getId() != null &&
            driverRepo.existsById(driverParameter.getId())) {

            DriverParameter existing = driverRepo
                    .findById(driverParameter.getId())
                    .orElseThrow();                // should exist

            copyFields(driverParameter, existing);
            driverRepo.save(existing);
            return "Driver updated successfully";
        }

        /* 3️⃣  CREATE path */
        if (driverParameter.getRole() == null) {
            driverParameter.setRole("DRIVER");     // sensible default
        }
        driverRepo.save(driverParameter);
        return "Driver created successfully";
    }

    /* --------------------------------------------------
     * AUTHENTICATION
     * -------------------------------------------------- */
    @Override
    public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver) {
        LoginResponseDriver resp = new LoginResponseDriver();

        DriverParameter driver =
                driverRepo.findByUsername(loginParamDriver.getUsername());

        if (driver != null &&
            driver.getPassword().equals(loginParamDriver.getPassword())) {

            resp.setSuccess(true);
            resp.setStatus("Success");
            resp.setDriver(Optional.of(driver));
            return resp;
        }

        resp.setSuccess(false);
        resp.setStatus("Invalid Username or Password");
        return resp;
    }

    /* --------------------------------------------------
     * QUERY helpers
     * -------------------------------------------------- */
    @Override
    public List<DriverParameter> getDriversByVehicleNumber(String vehicleNumber) {
        return driverRepo.findByVehicleNumber(vehicleNumber);
    }

    @Override
    public DriverParameter getDriverById(int id) {
        return driverRepo.findById(id).orElse(null);
    }

    @Override
    public DriverParameter findByUsername(String username) {
        return driverRepo.findByUsername(username);
    }

    /* --------------------------------------------------
     * UPDATE (via controller) – PUT /driver/update/{id}
     * -------------------------------------------------- */
    @Override
    @Transactional
    public String updateDriver(DriverParameter driverParameter) {

        if (driverParameter.getId() == null) {
            return "Driver ID is required for update";
        }

        DriverParameter existing = driverRepo
                .findById(driverParameter.getId())
                .orElse(null);

        if (existing == null) {
            return "Driver not found";
        }

        /* FK update (optional) */
        if (driverParameter.getOwner() != null &&
            driverParameter.getOwner().getUsername() != null) {

            OwnerParameter owner = ownerRepo
                    .findByUsername(driverParameter.getOwner().getUsername())
                    .orElseThrow(() ->
                         new IllegalArgumentException(
                             "Owner not found with username: "
                             + driverParameter.getOwner().getUsername()));
            existing.setOwner(owner);
        }

        copyFields(driverParameter, existing);
        driverRepo.save(existing);
        return "Driver updated successfully";
    }

    /* --------------------------------------------------
     * DELETE
     * -------------------------------------------------- */
    @Override
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
}
