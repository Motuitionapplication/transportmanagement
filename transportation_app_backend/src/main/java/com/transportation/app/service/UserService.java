package com.transportation.app.service;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginParamDriver;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.LoginResponseDriver;
import com.transportation.app.binding.User;
import com.transportation.app.constants.UserRoles;
import com.transportation.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Save a regular admin
    public String saveAdmin(User user) {
        user.setRole(UserRoles.ADMIN); // 'A'
        userRepo.save(user);
        return "Admin saved successfully.";
    }

    // Save a super admin
    public String saveSuperAdmin(User user) {
        user.setRole(UserRoles.SUPER_ADMIN); // maybe 'S'
        userRepo.save(user);
        return "Super Admin saved successfully.";
    }

    // Get all regular admins
    public List<User> getAdmins() {
        return userRepo.findByRole(UserRoles.ADMIN); // 'A'
    }

    // Get all super admins
    public List<User> getSuperAdmins() {
        return userRepo.findByRole(UserRoles.SUPER_ADMIN); // 'S'
    }

    // Promote existing user to regular admin
    public String assignAdmin(String username) {
        User user = userRepo.findById(username).orElse(null);
        if (user == null) {
            return "User not found.";
        }
        user.setRole(UserRoles.ADMIN); // 'A'
        userRepo.save(user);
        return "User promoted to admin.";
    }

    // Promote existing user to super admin
    public String assignSuperAdmin(String username) {
        User user = userRepo.findById(username).orElse(null);
        if (user == null) {
            return "User not found.";
        }
        user.setRole(UserRoles.SUPER_ADMIN); // 'S'
        userRepo.save(user);
        return "User promoted to super admin.";
    }

//    public LoginResponseDriver checkLogin(LoginParamDriver loginParamDriver) {
//        LoginResponseDriver resp = new LoginResponseDriver();
//
//        Optional<DriverParameter> driverOpt = driverRepo.findByUsername(loginParamDriver.getUsername());
//
//        if (driverOpt.isPresent()) {
//            DriverParameter driver = driverOpt.get();
//            if (driver.getPassword().equals(loginParamDriver.getPassword())) {
//                resp.setSuccess(true);
//                resp.setStatus("Success");
//                resp.setDriver(Optional.of(driver));
//                return resp;
//            }
//        }
//
//        resp.setSuccess(false);
//        resp.setStatus("Invalid Username or Password");
//        return resp;
//    }

   
}
