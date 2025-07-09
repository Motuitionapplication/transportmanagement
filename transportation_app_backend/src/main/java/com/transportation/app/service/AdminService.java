package com.transportation.app.service;

import com.transportation.app.binding.Admin;
import com.transportation.app.binding.LoginParamAdmin;
import com.transportation.app.binding.LoginResponseAdmin;
import com.transportation.app.constants.UserRoles;
import com.transportation.app.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

//    // Save a regular admin
//    public String saveAdmin(Admin admin) {
//        admin.setRole(UserRoles.ADMIN); // 'A'
//        adminRepo.save(admin);
//        return "Admin saved successfully.";
//    }

    // Save a super admin only if not already present
    public String saveAdminFlexible(Admin admin) {
        if (UserRoles.SUPER_ADMIN.equals(admin.getRole())) {
            List<Admin> existingSA = adminRepo.findByRole(UserRoles.SUPER_ADMIN);
            if (!existingSA.isEmpty()) {
                return "Super Admin already exists. Cannot create another.";
            }
            admin.setRole(UserRoles.SUPER_ADMIN);
            adminRepo.save(admin);
            return "Super Admin saved successfully.";
        } else if (UserRoles.ADMIN.equals(admin.getRole())) {
            admin.setRole(UserRoles.ADMIN);
            adminRepo.save(admin);
            return "Admin saved successfully.";
        } else {
            return "Invalid role. Use 'A' for Admin or 'SA' for Super Admin.";
        }
    }



//    // Get all regular admins
//    public List<Admin> getAdmins() {
//        return adminRepo.findByRole(UserRoles.ADMIN); // 'A'
//    }

    // Get all admins (regular and super)
    public List<Admin> getSuperAdmins() {
        List<Admin> superAdmins = adminRepo.findByRole(UserRoles.SUPER_ADMIN);
        List<Admin> admins = adminRepo.findByRole(UserRoles.ADMIN);

        superAdmins.addAll(admins); // combine both lists
        return superAdmins;
    }


//    // Promote existing user to regular admin
//    public String assignAdmin(String username) {
//        Admin admin = adminRepo.findById(username).orElse(null);
//        if (admin == null) {
//            return "User not found.";
//        }
//        admin.setRole(UserRoles.ADMIN); // 'A'
//        adminRepo.save(admin);
//        return "User promoted to admin.";
//    }

    // Assign super admin by promoting a user and demoting the current super admin
    public String assignSuperAdmin(String username) {
        Admin newSuperAdmin = adminRepo.findById(username).orElse(null);

        if (newSuperAdmin == null) {
            return "User not found.";
        }

        // Find and demote existing super admin
        List<Admin> existingSuperAdmins = adminRepo.findByRole(UserRoles.SUPER_ADMIN);
        for (Admin admin : existingSuperAdmins) {
            if (!admin.getUsername().equals(username)) {
                admin.setRole(UserRoles.ADMIN); // demote to 'A'
                adminRepo.save(admin);
            }
        }

        // Promote the new user
        newSuperAdmin.setRole(UserRoles.SUPER_ADMIN); // promote to 'SA'
        adminRepo.save(newSuperAdmin);

        return "User promoted to Super Admin. Previous Super Admin demoted to Admin.";
    }


    public LoginResponseAdmin checkLogin(LoginParamAdmin loginParamAdmin) {
        LoginResponseAdmin response = new LoginResponseAdmin();

        Admin admin= adminRepo.findByUsername(loginParamAdmin.getUsername());

        if (admin != null && admin.getPassword().equals(loginParamAdmin.getPassword())) {
            response.setSuccess(true);
            response.setStatus("Login successful");
            response.setUser(Optional.of(admin));
        } else {
            response.setSuccess(false);
            response.setStatus("Invalid username or password");
            response.setUser(Optional.empty());
        }

        return response;
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
