package com.transportmanagementfrontend;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String age;
    private String address;
    private String email;
    private String phone;
    private String password;
    private String role;

    public RegisterRequest(String firstName, String lastName, String age, String address, String email, String phone, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address= address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
}
