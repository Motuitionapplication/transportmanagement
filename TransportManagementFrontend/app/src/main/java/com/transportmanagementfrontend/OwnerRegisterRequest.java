package com.transportmanagementfrontend;

public class OwnerRegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String fatherName;
    private String address;
    private String email;
    private String vehicleNumber;
    private String username;
    private String password;
    private String role;

    public OwnerRegisterRequest(String firstName, String lastName, String phone, String fatherName, String address, String email, String vehicleNumber, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.fatherName = fatherName;
        this.address = address;
        this.email = email;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
