package com.transportation.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.repo.DriverRepository;

/**
 * Service implementation for {@link DriverParameter} entity operations.
 * <p>
 * Note: This class mirrors the behaviour of {@code OwnerServiceImpl} so that
 * driver–related CRUD operations behave consistently with owner–related ones.
 * Extensive inline comments have been added for learning / walkthrough
 * purposes. Remove or trim comments once you are comfortable with the flow.
 */
@Service
public class DriverServiceImpl implements DriverService {

    /* --------------------------------------------------
     * Dependencies
     * -------------------------------------------------- */
    @Autowired
    private DriverRepository driverRepo;              // Spring Data repository for DriverParameter

    /**
     * Constructor‑based injection preferred for testability. The field is still
     * annotated with {@code @Autowired} so legacy code continues working even
     * if Spring falls back to field injection.
     */
    public DriverServiceImpl(DriverRepository driverRepo) {
        this.driverRepo = driverRepo;
    }

    /* --------------------------------------------------
     * CREATE or UPDATE
     * -------------------------------------------------- */
    /**
     * If {@code driverParameter.id} is present and found in the DB, update that
     * record; otherwise create a new one. Mirrors the logic in
     * {@code OwnerServiceImpl#createOrUpdateOwner} so your REST behaviour stays
     * predictable across entities.
     */
    @Override
    @Transactional
    public String createOrUpdateDriver(DriverParameter driverParameter) {

        // ------------------------------------------------------------
        // UPDATE path – ID present *and* driver already exists
        // ------------------------------------------------------------
        if (driverParameter.getId() != null) {
            Optional<DriverParameter> optionalDriver = driverRepo.findById(driverParameter.getId());

            if (optionalDriver.isPresent()) {
                DriverParameter existingDriver = optionalDriver.get();

                /* ---------- BASIC FIELDS ---------- */
                existingDriver.setFirstName(driverParameter.getFirstName());
                existingDriver.setLastName(driverParameter.getLastName());
                existingDriver.setPhone(driverParameter.getPhone());
                existingDriver.setUsername(driverParameter.getUsername());
                existingDriver.setPassword(driverParameter.getPassword());

                /* ---------- DRIVING‑LICENCE DETAILS ---------- */
                existingDriver.setDlNumber(driverParameter.getDlNumber());
                existingDriver.setDlType(driverParameter.getDlType());

                /* ---------- CONTACT / PROFILE ---------- */
                existingDriver.setEmail(driverParameter.getEmail());
                existingDriver.setFatherOrHusbandName(driverParameter.getFatherOrHusbandName());

                /* ---------- DOCUMENTS & MEDIA ---------- */
                existingDriver.setPassportPhoto(driverParameter.getPassportPhoto());
                existingDriver.setIdentityProofType(driverParameter.getIdentityProofType());
                existingDriver.setIdentityProofFilePath(driverParameter.getIdentityProofFilePath());
                existingDriver.setInsurancePaperFilePath(driverParameter.getInsurancePaperFilePath());

                /* ---------- HEALTH & VEHICLE ---------- */
                existingDriver.setBloodGroup(driverParameter.getBloodGroup());
                existingDriver.setVehicleNumber(driverParameter.getVehicleNumber());

                /* ---------- ROLE ---------- */
                existingDriver.setSelectedRole(driverParameter.getSelectedRole());

                // Persist the updates in a single transaction
                driverRepo.save(existingDriver);
                return "Driver updated successfully";
            }
        }

        // ------------------------------------------------------------
        // CREATE path – new entity (no ID, or ID not found)
        // ------------------------------------------------------------
        driverRepo.save(driverParameter);
        return "Driver created successfully";
    }

    /* --------------------------------------------------
     * AUTHENTICATION
     * -------------------------------------------------- */
    /**
     * Validates driver credentials and returns a response DTO used by the
     * frontend / mobile client.
     */
    @Override
    public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver) {
        LoginResponseDriver response = new LoginResponseDriver();
        try {
            // Query by username (unique)
            DriverParameter driver = driverRepo.findByUsername(loginParamDriver.getUsername());

            // Validate credentials
            if (driver != null && driver.getPassword().equals(loginParamDriver.getPassword())) {
                response.setSuccess(true);
                response.setStatus("Success");
                response.setDriver(Optional.of(driver));
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace(); // consider SLF4J logger in production
        }

        // Fallback – invalid credentials
        response.setStatus("Invalid Username or Password");
        response.setSuccess(false);
        return response;
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
     * UPDATE / DELETE via controller‑initiated calls
     * -------------------------------------------------- */
    @Override
    public String updateDriver(DriverParameter driverParameter) {
        if (driverParameter.getId() == null || driverParameter.getId() == 0) {
            return "Driver ID is required for update";
        }

        Optional<DriverParameter> optionalDriver = driverRepo.findById(driverParameter.getId());
        if (optionalDriver.isEmpty()) {
            return "Driver not found";
        }

        DriverParameter existingDriver = optionalDriver.get();
        // Re‑use the field‑copy logic to keep code DRY
        existingDriver.setFirstName(driverParameter.getFirstName());
        existingDriver.setLastName(driverParameter.getLastName());
        existingDriver.setPhone(driverParameter.getPhone());
        existingDriver.setUsername(driverParameter.getUsername());
        existingDriver.setPassword(driverParameter.getPassword());
        existingDriver.setDlNumber(driverParameter.getDlNumber());
        existingDriver.setVehicleNumber(driverParameter.getVehicleNumber());
        existingDriver.setSelectedRole(driverParameter.getSelectedRole());
        existingDriver.setEmail(driverParameter.getEmail());

        driverRepo.save(existingDriver);
        return "Driver updated successfully";
    }

    @Override
    public String deleteDriver(int id) {
        if (driverRepo.existsById(id)) {
            driverRepo.deleteById(id);
            return "Driver deleted successfully";
        }
        return "Driver not found";
    }
}
