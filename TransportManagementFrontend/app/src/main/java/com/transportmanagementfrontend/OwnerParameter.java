package com.transportmanagementfrontend;

import java.io.Serializable;

public class OwnerParameter implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String fatherName;
    private String address;
    private String vehicleNumber;
    private String username;
    private String password;
    private String role;

    // Default Constructor
    public OwnerParameter() {
    }

    // Parameterized Constructor
    public OwnerParameter(int id, String firstName, String lastName, String phone, String fatherName, String address,
                          String vehicleNumber, String username, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.fatherName = fatherName;
        this.address = address;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    // Getters and Setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
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
}
