package com.transportation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transportation.app.binding.CustomerParameter;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;

import com.transportation.app.repo.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository userRepo;

    @Autowired
    private SmsService fast2SMSService;

    @Autowired
    private EmailService emailService;

    // CREATE USER
    public String createUser(CustomerParameter userParameter) {
        userRepo.save(userParameter);
        return "User created";
    }

    // LOGIN CHECK
    public LoginResponse checkLogin(LoginParam loginParam) {
        LoginResponse response = new LoginResponse();
        Optional<CustomerParameter> user = Optional.empty();

        try {
            user = Optional.ofNullable(userRepo.findByUsername(loginParam.getUsername()));
            if (user.isPresent() && user.get().getPassword().equals(loginParam.getPassword())) {
                response.setStatus("Success");
                response.setSuccess(true);
                response.setUser(user);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setStatus("Invalid User Name & Password");
        response.setSuccess(false);
        return response;
    }

    // GENERATE OTP
    public String generateOTP(String phone) {
        String mobileStr = String.valueOf(phone);
        String otp = fast2SMSService.sendOTP(mobileStr);

        Optional<CustomerParameter> userOptional = userRepo.findByPhone(phone);
        if (userOptional.isPresent()) {
        	CustomerParameter user = userOptional.get();
            user.setOtp(otp);
            userRepo.save(user);
            return "OTP sent successfully to " + phone;
        } else {
            return "User with mobile number " + phone + " not found.";
        }
    }

    // FORGOT PASSWORD
    public String forgotPassword(String email) {
    	CustomerParameter user = userRepo.findByEmail(email);

        if (user != null) {
            String subject = "Forgot Password Request";
            String message = "Hey, " + user.getUsername() + ", this is your password: " + user.getPassword();
            emailService.sendSimpleMessage(user.getEmail(), subject, message);
            return "Password sent to your email.";
        } else {
            return "Email not valid.";
        }
    }
}
