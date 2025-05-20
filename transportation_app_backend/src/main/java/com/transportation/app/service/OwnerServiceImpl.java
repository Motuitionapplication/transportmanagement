package com.transportation.app.service;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.repo.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepo;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    /* --------------------------------------------------
       CREATE
       -------------------------------------------------- */
    @Override
    public String createOrUpdateOwner(OwnerParameter ownerParameter) {
        if (ownerParameter.getId() != null) {
            // Check if owner exists in DB
            Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());
            if (optionalOwner.isPresent()) {
                OwnerParameter existingOwner = optionalOwner.get();

                // Update basic fields
                existingOwner.setFirstName(ownerParameter.getFirstName());
                existingOwner.setLastName(ownerParameter.getLastName());
                existingOwner.setPhone(ownerParameter.getPhone());
                existingOwner.setUsername(ownerParameter.getUsername());
                existingOwner.setPassword(ownerParameter.getPassword());
                existingOwner.setFatherName(ownerParameter.getFatherName());
                existingOwner.setVehicleNumber(ownerParameter.getVehicleNumber());
                existingOwner.setRole(ownerParameter.getRole());
                existingOwner.setEmail(ownerParameter.getEmail());

                // Update proof/verification fields
                existingOwner.setAddressProofType(ownerParameter.getAddressProofType());
                existingOwner.setAddressProofNumber(ownerParameter.getAddressProofNumber());
                existingOwner.setAddressProofVerified(ownerParameter.isAddressProofVerified());
                existingOwner.setIdentityProofType(ownerParameter.getIdentityProofType());
                existingOwner.setIdentityProofNumber(ownerParameter.getIdentityProofNumber());
                existingOwner.setIdentityProofVerified(ownerParameter.isIdentityProofVerified());

                // Update embedded objects
                existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
                existingOwner.setAccountDetails(ownerParameter.getAccountDetails());
                existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
                existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());

                ownerRepo.save(existingOwner);
                return "Owner updated successfully";
            }
        }

        // If ID is null or not found, treat as create
        ownerRepo.save(ownerParameter);
        return "Owner created successfully";
    }



    /* --------------------------------------------------
       LOGIN
       -------------------------------------------------- */
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
       FIND BY USERNAME
       -------------------------------------------------- */
    @Override
    public OwnerParameter findByUsername(String username) {
        return ownerRepo.findByUsername(username);
    }

    /* --------------------------------------------------
       UPDATE  –  *UP*date or in*SERT*
       -------------------------------------------------- */
    @Override
    public String updateOwner(OwnerParameter ownerParameter) {

        // Require an ID
        if (ownerParameter.getId() == null || ownerParameter.getId() == 0) {
            return "Owner ID is required for update";
        }

        Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());

        /* ---- 1️⃣ UPDATE (id exists) ------------------------------------- */
        if (optionalOwner.isPresent()) {

            OwnerParameter existingOwner = optionalOwner.get();

            // 🔄 copy fields (only the ones you want to make mutable)
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

            // Present address
            if (ownerParameter.getPresentAddress() != null) {
                if (existingOwner.getPresentAddress() == null) {
                    existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
                } else {
                    existingOwner.getPresentAddress().updateFrom(ownerParameter.getPresentAddress());
                }
            }

            // Permanent address
            if (ownerParameter.getPermanentAddress() != null) {
                if (existingOwner.getPermanentAddress() == null) {
                    existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());
                } else {
                    existingOwner.getPermanentAddress().updateFrom(ownerParameter.getPermanentAddress());
                }
            }

            // Vehicle details
            if (ownerParameter.getVehicleDetails() != null) {
                if (existingOwner.getVehicleDetails() == null) {
                    existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
                } else {
                    existingOwner.getVehicleDetails().updateFrom(ownerParameter.getVehicleDetails());
                }
            }

            // Account details
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

        /* ---- 2️⃣ INSERT (id does NOT exist) ----------------------------- */
        ownerRepo.save(ownerParameter);   // insert new row with supplied ID
        return "Owner not found. New owner created";
    }

    /* --------------------------------------------------
       DELETE
       -------------------------------------------------- */
    @Override
    public String deleteOwner(int ownerId) {
        if (ownerRepo.existsById(ownerId)) {
            ownerRepo.deleteById(ownerId);
            return "Owner deleted successfully";
        }
        return "Owner not found";
    }
}
