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

    @Autowired
    private SmsService fast2SMSService;

    @Override
    public String createUser(UserParameter userParameter) {
        userRepo.save(userParameter);
        return "User created";
    }

    @Override
    public LoginResponse checkLogin(LoginParam loginParam) {
        LoginResponse response = new LoginResponse();
        Optional<UserParameter> user = Optional.empty();
        try {
            user = Optional.ofNullable(userRepo.findByUsername(loginParam.getUsername()));
            if (user.get().getPassword().equals(loginParam.getPassword())) {
                response.setStatus("Success");
                response.setSuccess(true);
                response.setUser(user);

                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus("Invalid User Name & Password");
        return response;
    }

    /**
     * This method generates an OTP and sends it via Fast2SMS.
     *
     * @param mobileNumber the mobile number for which the OTP is generated
     * @return a response string indicating success or failure
     */
    @Override
    public String generateOTP(String phone) {
        // Convert mobile number to string (ensure it is in the correct format)
        String mobileStr = String.valueOf(phone);
        
        // Send the OTP via Fast2SMS and capture the generated OTP
        String otp = fast2SMSService.sendOTP(mobileStr);

        // If needed, store the OTP in the user record or a separate OTP storage
        Optional<UserParameter> userOptional = userRepo.findByPhone(phone);
        if (userOptional.isPresent()) {
            UserParameter user = userOptional.get();
            user.setOtp(otp);
            userRepo.save(user);
            return "OTP sent successfully to " + phone;
        } else {
            return "User with mobile number " + phone + " not found.";
        }
    }

	
}
