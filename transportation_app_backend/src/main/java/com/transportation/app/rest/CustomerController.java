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

import com.transportation.app.binding.CustomerParameter;
import com.transportation.app.binding.LoginParam;
import com.transportation.app.binding.LoginResponse;
import com.transportation.app.service.CustomerService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> registerUser(@RequestBody CustomerParameter userParameter) {
        String result = userService.createUser(userParameter);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginParam loginParam) {
        LoginResponse result = userService.checkLogin(loginParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/generateOTP/{phone}")
    public ResponseEntity<String> generateOTP(@PathVariable String phone) {
        String response = userService.generateOTP(phone);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        email = email.trim();
        String result = userService.forgotPassword(email);
        if ("Password sent to your email.".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
