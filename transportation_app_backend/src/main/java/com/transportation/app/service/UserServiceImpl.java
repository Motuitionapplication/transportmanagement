package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.UserParameter;
import com.transportation.app.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public String createUser(UserParameter userParameter) {
		userRepo.save(userParameter);
		return "user created";
	}

//	@Override
//	public LoginResponse checkLogin(LoginParam loginParam) {
//		Optional<UserParameter> user = userRepo.findByUsernameAndPassword(loginParam.getUsername(),
//				loginParam.getPassword());
//
//		LoginResponse response = new LoginResponse();
//
//		if (user.isPresent()) {
//			response.setStatus("Success");
//		} else {
//			response.setStatus("Invalid Username or Password");
//		}
//
//		return response;
//	}

	
	/**
	 * This method is used for userLogin & Password.
	 * @author vibek
	 */
	@Override
	public LoginResponse checkLogin(LoginParam loginParam) {
		LoginResponse response = new LoginResponse();
		Optional<UserParameter> user = java.util.Optional.empty();
		try {
			user = Optional.ofNullable(userRepo.findByUsername(loginParam.getUsername()));

			if (user.get().getPassword().equals(loginParam.getPassword())) {
			response.setStatus("Success");
			
			return response;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		response.setStatus("Invalid User Name & Password");

		return response;

		
	}

}