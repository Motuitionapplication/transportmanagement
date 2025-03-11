package com.transportmanagementfrontend;

import java.io.Serializable;

public class OwnerParameter implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String vehicleType;
    private String username;
    private String password;
    private String role;

    // Default Constructor
    public OwnerParameter() {
    }

    // Parameterized Constructor
    public OwnerParameter(int id, String firstName, String lastName, String phone, String address,
                          String vehicleType, String username, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.vehicleType = vehicleType;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
