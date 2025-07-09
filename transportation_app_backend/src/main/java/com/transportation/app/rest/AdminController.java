/**
 * @Vibek
 * This is the Admin Controller for handling admin operations.
 */

package com.transportation.app.rest;

import com.transportation.app.binding.Admin;
import com.transportation.app.binding.LoginParamAdmin;
import com.transportation.app.binding.LoginResponseAdmin;
import com.transportation.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ Save Super Admin (only if one doesn't exist)
    @PostMapping("/save-flexible-Admin")
    public ResponseEntity<String> saveAdminFlexible(@RequestBody Admin admin) {
        String response = adminService.saveAdminFlexible(admin);
        return ResponseEntity.ok(response);
    }


//    // ✅ Get all regular admins
//    @GetMapping("/all")
//    public ResponseEntity<List<Admin>> getAdmins() {
//        List<Admin> admins = adminService.getAdmins();
//        return ResponseEntity.ok(admins);
//    }

    // ✅ Get all super admins
    @GetMapping("/Get_All_Admins")
    public ResponseEntity<List<Admin>> getSuperAdmins() {
        List<Admin> superAdmins = adminService.getSuperAdmins();
        return ResponseEntity.ok(superAdmins);
    }

    // ✅ Assign a user as Super Admin (demotes current SA)
    @PutMapping("/Assign-super-Admin/{username}")
    public ResponseEntity<String> assignSuperAdmin(@PathVariable String username) {
        String response = adminService.assignSuperAdmin(username);
        return ResponseEntity.ok(response);
    }

    // ✅ Admin Login
    @PostMapping("/login-Admin")
    public ResponseEntity<LoginResponseAdmin> loginAdmin(@RequestBody LoginParamAdmin loginParamUser) {
        LoginResponseAdmin result = adminService.checkLogin(loginParamUser);
        return ResponseEntity.ok(result);
    }

    // 🔄 Optional: You can also uncomment below to allow assigning regular admins
    /*
    @PutMapping("/assign/{username}")
    public ResponseEntity<String> assignAdmin(@PathVariable String username) {
        String response = adminService.assignAdmin(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveAdmin(@RequestBody Admin admin) {
        String response = adminService.saveAdmin(admin);
        return ResponseEntity.ok(response);
    }
    */
}
