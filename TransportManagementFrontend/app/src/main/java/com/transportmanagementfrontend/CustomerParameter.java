package com.transportmanagementfrontend;

import java.io.Serializable;

public class CustomerParameter implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String email;
    private String username;
    private String password;
    private String role;
    private String phone;
    private String otp;

    // Default Constructor
    public CustomerParameter() {
    }

    // Parameterized Constructor
    public CustomerParameter(int id, String firstName, String lastName, int age, String address,
                             String email, String username, String password, String role, String phone, String otp) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.email = email; // Added email parameter
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.otp = otp;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
