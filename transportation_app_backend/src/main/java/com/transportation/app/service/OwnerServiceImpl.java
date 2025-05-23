package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.repo.OwnerRepository;

/**
 * Service implementation for {@link OwnerParameter} entity operations.
 * <p>
 * This file parallels {@code DriverServiceImpl} so both Owner and Driver
 * entities share a consistent CRUD+Auth behaviour. Each major section is
 * wrapped in clear comment blocks (CREATE, LOGIN, UPDATE, DELETE, etc.) so you
 * can quickly scan and understand the flow. Remove or reduce comments once the
 * class makes sense to you in day‑to‑day work.
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    /* --------------------------------------------------
     * Dependencies
     * -------------------------------------------------- */
    private final OwnerRepository ownerRepo;

    /**
     * Constructor‑based injection enables easier unit testing and avoids field
     * injection pitfalls. Field still marked @Autowired for backwards
     * compatibility if you ever fall back to field injection.
     */
    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    /* --------------------------------------------------
     * CREATE or UPDATE (Upsert)
     * -------------------------------------------------- */
    @Override
    @Transactional
    public String createOrUpdateOwner(OwnerParameter ownerParameter) {

        // ------------------------------------------------------------
        // UPDATE path – non‑null ID and entity exists
        // ------------------------------------------------------------
        if (ownerParameter.getId() != null) {
            Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());

            if (optionalOwner.isPresent()) {
                OwnerParameter existingOwner = optionalOwner.get();

                /* ---------- BASIC FIELDS ---------- */
                existingOwner.setFirstName(ownerParameter.getFirstName());
                existingOwner.setLastName(ownerParameter.getLastName());
                existingOwner.setPhone(ownerParameter.getPhone());
                existingOwner.setUsername(ownerParameter.getUsername());
                existingOwner.setPassword(ownerParameter.getPassword());
                existingOwner.setFatherName(ownerParameter.getFatherName());
                existingOwner.setVehicleNumber(ownerParameter.getVehicleNumber());
                existingOwner.setRole(ownerParameter.getRole());
                existingOwner.setEmail(ownerParameter.getEmail());

                /* ---------- PROOF / VERIFICATION ---------- */
                existingOwner.setAddressProofType(ownerParameter.getAddressProofType());
                existingOwner.setAddressProofNumber(ownerParameter.getAddressProofNumber());
                existingOwner.setAddressProofVerified(ownerParameter.isAddressProofVerified());
                existingOwner.setIdentityProofType(ownerParameter.getIdentityProofType());
                existingOwner.setIdentityProofNumber(ownerParameter.getIdentityProofNumber());
                existingOwner.setIdentityProofVerified(ownerParameter.isIdentityProofVerified());

                /* ---------- EMBEDDED OBJECTS ---------- */
                existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
                existingOwner.setAccountDetails(ownerParameter.getAccountDetails());
                existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
                existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());

                ownerRepo.save(existingOwner);
                return "Owner updated successfully";
            }
        }

        // ------------------------------------------------------------
        // CREATE path – new entity (null ID or ID not found)
        // ------------------------------------------------------------
        ownerRepo.save(ownerParameter);
        return "Owner created successfully";
    }

    /* --------------------------------------------------
     * LOGIN (Authentication)
     * -------------------------------------------------- */
    @Override
    public LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner) {
        LoginResponseOwner response = new LoginResponseOwner();

        OwnerParameter owner = ownerRepo.findByUsername(loginParamOwner.getUsername());

        if (owner != null && owner.getPassword().equals(loginParamOwner.getPassword())) {
            response.setStatus("Success");
            response.setSuccess(true);
            response.setOwner(Optional.of(owner));
        } else {
            response.setStatus("Invalid Owner Name & Password");
            response.setSuccess(false);
        }
        return response;
    }

    /* --------------------------------------------------
     * FIND by Username (utility)
     * -------------------------------------------------- */
    @Override
    public OwnerParameter findByUsername(String username) {
        return ownerRepo.findByUsername(username);
    }

    /* --------------------------------------------------
     * UPDATE (explicit) – upsert logic used via controller "update" endpoint
     * -------------------------------------------------- */
    @Override
    @Transactional
    public String updateOwner(OwnerParameter ownerParameter) {

        // 1️⃣ Guard clause – ID is mandatory for update
        if (ownerParameter.getId() == null || ownerParameter.getId() == 0) {
            return "Owner ID is required for update";
        }

        // 2️⃣ Try to fetch existing entity
        Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());

        /* ---- UPDATE branch ------------------------------------------------ */
        if (optionalOwner.isPresent()) {
            OwnerParameter existingOwner = optionalOwner.get();

            // Copy whichever fields you allow to be mutable
            existingOwner.setFirstName(ownerParameter.getFirstName());
            existingOwner.setLastName(ownerParameter.getLastName());
            existingOwner.setPhone(ownerParameter.getPhone());
            existingOwner.setUsername(ownerParameter.getUsername());
            existingOwner.setPassword(ownerParameter.getPassword());
            existingOwner.setFatherName(ownerParameter.getFatherName());
            existingOwner.setVehicleNumber(ownerParameter.getVehicleNumber());
            existingOwner.setRole(ownerParameter.getRole());
            existingOwner.setEmail(ownerParameter.getEmail());
            existingOwner.setAddressProofType(ownerParameter.getAddressProofType());
            existingOwner.setAddressProofNumber(ownerParameter.getAddressProofNumber());
            existingOwner.setAddressProofVerified(ownerParameter.isAddressProofVerified());
            existingOwner.setIdentityProofType(ownerParameter.getIdentityProofType());
            existingOwner.setIdentityProofNumber(ownerParameter.getIdentityProofNumber());
            existingOwner.setIdentityProofVerified(ownerParameter.isIdentityProofVerified());

            // Embedded value objects – deep copy or replace as needed
            if (ownerParameter.getPresentAddress() != null) {
                if (existingOwner.getPresentAddress() == null) {
                    existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
                } else {
                    existingOwner.getPresentAddress().updateFrom(ownerParameter.getPresentAddress());
                }
            }
            if (ownerParameter.getPermanentAddress() != null) {
                if (existingOwner.getPermanentAddress() == null) {
                    existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());
                } else {
                    existingOwner.getPermanentAddress().updateFrom(ownerParameter.getPermanentAddress());
                }
            }
            if (ownerParameter.getVehicleDetails() != null) {
                if (existingOwner.getVehicleDetails() == null) {
                    existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
                } else {
                    existingOwner.getVehicleDetails().updateFrom(ownerParameter.getVehicleDetails());
                }
            }
            if (ownerParameter.getAccountDetails() != null) {
                if (existingOwner.getAccountDetails() == null) {
                    existingOwner.setAccountDetails(ownerParameter.getAccountDetails());
                } else {
                    existingOwner.getAccountDetails().updateFrom(ownerParameter.getAccountDetails());
                }
            }

            ownerRepo.save(existingOwner);
            return "Owner updated successfully";
        }

        /* ---- INSERT branch ----------------------------------------------- */
        ownerRepo.save(ownerParameter); // insert new row with provided ID
        return "Owner not found. New owner created";
    }

    /* --------------------------------------------------
     * DELETE
     * -------------------------------------------------- */
    @Override
    public String deleteOwner(int id) {
        if (ownerRepo.existsById(id)) {
            ownerRepo.deleteById(id);
            return "Owner deleted successfully";
        }
        return "Owner not found";
    }

    /* --------------------------------------------------
     * GET by ID (simple fetch helper)
     * -------------------------------------------------- */
    @Override
    public OwnerParameter getOwnerById(int id) {
        return ownerRepo.findById(id).orElse(null);
    }
}
