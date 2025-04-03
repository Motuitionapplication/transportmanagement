package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;
import com.transportation.app.repo.OwnerRepository;

@Service
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private OwnerRepository ownerRepo;

	@Override
	public String createOwner(OwnerParameter ownerParameter) {
		ownerRepo.save(ownerParameter);
		return "Owner created";
	}

	@Override
	public LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner) {
		LoginResponseOwner response = new LoginResponseOwner();
        Optional<OwnerParameter> owner = Optional.empty();
        try {
        	owner = Optional.ofNullable(ownerRepo.findByUsername(loginParamOwner.getUsername()));
            if (owner.isPresent() && owner.get().getPassword().equals(loginParamOwner.getPassword())) {
                response.setStatus("Success");
                response.setSuccess(true);
                response.setOwner(owner);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus("Invalid Owner Name & Password");
        return response;
	}

	@Override
	public OwnerParameter findByUsername(String username) {
		// TODO Auto-generated method stub
		return ownerRepo.findByUsername(username);
	}

}
