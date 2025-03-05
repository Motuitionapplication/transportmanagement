package com.transportation.app.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Random;

@Service
public class SmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.trialNumber}")
    private String trialNumber;

    @jakarta.annotation.PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    /**
     * Generate a 6-digit OTP, send it to the given mobile number via Twilio,
     * and return the generated OTP.
     *
     * @param mobileNumber Mobile number to which the OTP will be sent.
     * @return The generated OTP as String.
     */
    public String sendOTP(String phone) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Compose the message
        String message = "Your OTP is: " + otp;

        // Send the SMS using Twilio
        Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(trialNumber),
                message
        ).create();

        System.out.println("OTP sent to " + phone + ": " + otp);

        // Return the OTP so you can store it if needed
        return otp;
    }
}
