package com.transportation.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class Fast2SMSService {

    @Value("${fast2sms.api.key}")
    private String apiKey;

    // Base URL for Fast2SMS API (as per their documentation)
    private final String FAST2SMS_URL = "https://www.fast2sms.com/dev/bulkV2";

    /**
     * Generate a 6-digit OTP, send it to the given mobile number via Fast2SMS,
     * and return the generated OTP.
     *
     * @param mobileNumber Mobile number to which the OTP will be sent.
     * @return The generated OTP as String.
     */
    public String sendOTP(String mobileNumber) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        
        // Compose the message
        String message = "Your OTP is: " + otp;
        // URL encode the message text
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        // Construct the complete URL
        String url = FAST2SMS_URL + "?authorization=" + apiKey +
                     "&message=" + encodedMessage +
                     "&language=english&route=q&numbers=" + mobileNumber;

        // Use RestTemplate to send the HTTP GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        System.out.println("Fast2SMS API Response: " + response.getBody());
        System.out.println("OTP sent to " + mobileNumber + ": " + otp);
        
        // Return the OTP so you can store it if needed
        return otp;
    }
}
