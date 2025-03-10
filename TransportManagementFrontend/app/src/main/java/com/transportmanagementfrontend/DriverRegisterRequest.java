package com.transportmanagementfrontend;

public class DriverRegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String dlNumber;
    private String vehicleNumber;
    private String username;
    private String password;
    private String role;

    public DriverRegisterRequest(String firstName, String lastName, String phone, String dlNumber, String vehicleNumber, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.dlNumber = dlNumber;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
