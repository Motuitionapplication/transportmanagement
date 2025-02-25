package com.transportation.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.binding.UserParameter;
import com.transportation.app.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> registerUser(@RequestBody UserParameter userParameter) {
        String result = userService.createUser(userParameter);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginParam loginParam) {
        LoginResponse result = userService.checkLogin(loginParam);
        System.out.println("line after exception");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/generateOTP/{mobileNumber}")
    public ResponseEntity<String> generateOTP(@PathVariable String mobileNumber) {
        String response = userService.generateOTP(mobileNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
