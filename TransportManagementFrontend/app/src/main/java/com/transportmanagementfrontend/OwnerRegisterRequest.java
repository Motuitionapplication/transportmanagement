package com.transportmanagementfrontend;

public class OwnerRegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private String vehicleType;
    private String username;
    private String password;
    private String role;

    public OwnerRegisterRequest(String firstName, String lastName, String phone, String address, String email, String vehicleType, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.vehicleType = vehicleType;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
