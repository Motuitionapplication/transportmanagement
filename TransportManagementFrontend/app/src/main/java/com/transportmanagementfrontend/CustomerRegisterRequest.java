package com.transportmanagementfrontend;

public class CustomerRegisterRequest {
    private String firstName;
    private String lastName;
    private String age;
    private String address;
    private String email;
    private String username;
    private String phone;
    private String password;
    private String role;

    public CustomerRegisterRequest(String firstName, String lastName, String age, String address, String email, String username, String phone, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address= address;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
}
