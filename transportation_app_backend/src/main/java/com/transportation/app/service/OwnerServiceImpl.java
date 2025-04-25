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

    @Override
    public String createOwner(OwnerParameter ownerParameter) {
        // Clear the ID and version to ensure the entity is new
        ownerParameter.setId(null);
        // If applicable, clear the version:
        // ownerParameter.setVersion(null);
        ownerRepo.save(ownerParameter);
        return "Owner created";
    }

    @Override
    public LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner) {
        LoginResponseOwner response = new LoginResponseOwner();
        try {
            OwnerParameter owner = ownerRepo.findByUsername(loginParamOwner.getUsername());
            if (owner != null && owner.getPassword().equals(loginParamOwner.getPassword())) {
                response.setStatus("Success");
                response.setSuccess(true);
                response.setOwner(Optional.of(owner));
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus("Invalid Owner Name & Password");
        response.setSuccess(false);
        return response;
    }

    @Override
    public OwnerParameter findByUsername(String username) {
        return ownerRepo.findByUsername(username);
    }

    @Override
    public String updateOwner(OwnerParameter ownerParameter) {
        if (ownerParameter.getId() == 0) {
            return "Owner ID is required for update";
        }

        Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());
        if (!optionalOwner.isPresent()) {
            return "Owner not found";
        }

        OwnerParameter existingOwner = optionalOwner.get();

        // Update basic info
        existingOwner.setFirstName(ownerParameter.getFirstName());
        existingOwner.setLastName(ownerParameter.getLastName());
        existingOwner.setPhone(ownerParameter.getPhone());
        existingOwner.setUsername(ownerParameter.getUsername());
        existingOwner.setPassword(ownerParameter.getPassword());
        existingOwner.setFatherName(ownerParameter.getFatherName());
        existingOwner.setVehicleNumber(ownerParameter.getVehicleNumber());
        existingOwner.setSelectedRole(ownerParameter.getSelectedRole());
        existingOwner.setEmail(ownerParameter.getEmail());
        existingOwner.setAddressProofType(ownerParameter.getAddressProofType());
        existingOwner.setAddressProofNumber(ownerParameter.getAddressProofNumber());
        existingOwner.setAddressProofVerified(ownerParameter.isAddressProofVerified());
        existingOwner.setIdentityProofType(ownerParameter.getIdentityProofType());
        existingOwner.setIdentityProofNumber(ownerParameter.getIdentityProofNumber());
        existingOwner.setIdentityProofVerified(ownerParameter.isIdentityProofVerified());

        // Update present address
        if (ownerParameter.getPresentAddress() != null) {
            if (existingOwner.getPresentAddress() == null) {
                existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
            } else {
                existingOwner.getPresentAddress().updateFrom(ownerParameter.getPresentAddress());
            }
        }

        // Update permanent address
        if (ownerParameter.getPermanentAddress() != null) {
            if (existingOwner.getPermanentAddress() == null) {
                existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());
            } else {
                existingOwner.getPermanentAddress().updateFrom(ownerParameter.getPermanentAddress());
            }
        }

        // Update vehicle details
        if (ownerParameter.getVehicleDetails() != null) {
            if (existingOwner.getVehicleDetails() == null) {
                existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
            } else {
                existingOwner.getVehicleDetails().updateFrom(ownerParameter.getVehicleDetails());
            }
        }

        // Update account details
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

    @Override
    public String deleteOwner(int ownerId) {
        if (ownerRepo.existsById(ownerId)) {
            ownerRepo.deleteById(ownerId);
            return "Owner deleted successfully";
        } else {
            return "Owner not found";
        }
    }
}
