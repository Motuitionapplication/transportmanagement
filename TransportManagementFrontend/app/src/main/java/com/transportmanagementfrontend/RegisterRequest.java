package com.transportmanagementfrontend;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String age;
    private String address;
    private String username;
    private String phone;
    private String password;
    private String role;

    public RegisterRequest(String firstName, String lastName, String age, String address, String username, String phone, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address= address;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
}
