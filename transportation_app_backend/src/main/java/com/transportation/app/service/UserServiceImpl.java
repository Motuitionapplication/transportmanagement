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

    /**
     * This method is used for user login with email and password.
     */
    @Override
    public LoginResponse checkLogin(LoginParam loginParam) {
        LoginResponse response = new LoginResponse();
        
        // Retrieve the user based on email safely using Optional
        Optional<UserParameter> userOptional = Optional.ofNullable(userRepo.findByEmail(loginParam.getEmail()));
        
        if (userOptional.isPresent()) {
            UserParameter user = userOptional.get();
            // Compare the provided password with the user's password
            if (user.getPassword().equals(loginParam.getPassword())) {
                response.setStatus("Success");
                response.setSuccess(true);
                return response;
            }
        }
        
        // If no user is found or password doesn't match, return an error status
        response.setStatus("Invalid Email & Password");
        return response;
    }
}
