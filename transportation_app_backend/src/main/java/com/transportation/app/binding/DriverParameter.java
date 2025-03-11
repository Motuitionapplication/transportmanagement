package com.transportation.app.binding;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DRIVER_REG_DETAILS")
public class DriverParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private String dlNumber;
    private String vehicleNumber;
    private String selectedRole;
    private String email;

    public DriverParameter() {
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
 
    public String getDlNumber() {
        return dlNumber;
    }
 
    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }
 
    public String getVehicleNumber() {
        return vehicleNumber;
    }
 
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
 
    public String getSelectedRole() {
        return selectedRole;
    }
 
    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
}
