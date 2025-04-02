package com.transportmanagementfrontend;

public class DriverRegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dlNumber;
    private String vehicleNumber;
    private String username;
    private String password;
    private String role;

    public DriverRegisterRequest(String firstName, String lastName, String phone, String  email, String dlNumber, String vehicleNumber, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dlNumber = dlNumber;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
