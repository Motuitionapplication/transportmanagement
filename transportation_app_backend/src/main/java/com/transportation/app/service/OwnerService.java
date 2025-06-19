package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.repo.OwnerRepository;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepo;

    @Autowired
    public OwnerService(OwnerRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Transactional
    public String createOrUpdateOwner(OwnerParameter ownerParameter) {
        if (ownerParameter.getId() != null) {
            Optional<OwnerParameter> optionalOwner = ownerRepo.findById(ownerParameter.getId());
            if (optionalOwner.isPresent()) {
                OwnerParameter existingOwner = optionalOwner.get();

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

                existingOwner.setVehicleDetails(ownerParameter.getVehicleDetails());
                existingOwner.setAccountDetails(ownerParameter.getAccountDetails());
                existingOwner.setPresentAddress(ownerParameter.getPresentAddress());
                existingOwner.setPermanentAddress(ownerParameter.getPermanentAddress());

                ownerRepo.save(existingOwner);
                return "Owner updated successfully";
            }
        }

        ownerRepo.save(ownerParameter);
        return "Owner created successfully";
    }

    public LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner) {
        LoginResponseOwner response = new LoginResponseOwner();

        Optional<OwnerParameter> optionalOwner = ownerRepo.findByUsername(loginParamOwner.getUsername());
        if (optionalOwner.isPresent() &&
            optionalOwner.get().getPassword().equals(loginParamOwner.getPassword())) {
            response.setStatus("Success");
            response.setSuccess(true);
            response.setOwner(optionalOwner);
        } else {
            response.setStatus("Invalid Owner Name & Password");
            response.setSuccess(false);
        }
        return response;
    }

    public OwnerParameter findByUsername(String username) {
        return ownerRepo.findByUsername(username).orElse(null);
    }

    public String deleteOwner(int id) {
        if (ownerRepo.existsById(id)) {
            ownerRepo.deleteById(id);
            return "Owner deleted successfully";
        }
        return "Owner not found";
    }

    public OwnerParameter getOwnerById(int id) {
        return ownerRepo.findById(id).orElse(null);
    }
}
