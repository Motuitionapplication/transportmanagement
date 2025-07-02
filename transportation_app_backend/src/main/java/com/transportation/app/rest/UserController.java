//package com.transportation.app.rest;
//
//import com.transportation.app.binding.User;
//import com.transportation.app.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // Save regular admin
//    @PostMapping("/save")
//    public String saveAdmin(@RequestBody User user) {
//        return userService.saveAdmin(user);
//    }
//    
//
//    // Get all regular admins
//    @GetMapping("/all")
//    public List<User> getAdmins() {
//        return userService.getAdmins();
//    }
//
//    // Promote to super admin
//    @PutMapping("/assign-super/{username}")
//    public String assignSuperAdmin(@PathVariable String username) {
//        return userService.assignSuperAdmin(username);
//    }
//}




/**
 * @Vibek
 * this is the all Controllers below, on top only selected ones, switch by commenting one part either up part or down part.
 */





package com.transportation.app.rest;

import com.transportation.app.binding.User;
import com.transportation.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;

    // Save regular admin
    @PostMapping("/save")
    public String saveAdmin(@RequestBody User user) {
        return userService.saveAdmin(user);
    }

    // Save super admin
    @PostMapping("/save-super")
    public String saveSuperAdmin(@RequestBody User user) {
        return userService.saveSuperAdmin(user);
    }

    // Get all regular admins
    @GetMapping("/all")
    public List<User> getAdmins() {
        return userService.getAdmins();
    }

    // Get all super admins
    @GetMapping("/super")
    public List<User> getSuperAdmins() {
        return userService.getSuperAdmins();
    }

    // Promote to admin
    @PutMapping("/assign/{username}")
    public String assignAdmin(@PathVariable String username) {
        return userService.assignAdmin(username);
    }

    // Promote to super admin
    @PutMapping("/assign-super/{username}")
    public String assignSuperAdmin(@PathVariable String username) {
        return userService.assignSuperAdmin(username);
    }
    
//    @PostMapping("/login")
//    public User loginAdmin(@RequestBody User loginParam) {
//        return userService.checkLogin(loginParam);
//    }

}
