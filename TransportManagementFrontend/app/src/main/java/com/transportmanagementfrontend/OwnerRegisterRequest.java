package com.transportmanagementfrontend;

import com.transportmanagementfrontend.AccountDetails;
import com.transportmanagementfrontend.Address;
import com.transportmanagementfrontend.VehicleDetails;

public class OwnerRegisterRequest {
    private String firstName, lastName, phone, fatherName, email, username, password, role, vehicleNumber;
    private String addressProofType, addressProofNumber, identityProofType, identityProofNumber;
    private Address presentAddress, permanentAddress;
    private AccountDetails accountDetails;
    private VehicleDetails vehicleDetails;

    public OwnerRegisterRequest(String firstName, String lastName, String phone, String fatherName, String email, String username, String password, String role,
                                String addressProofType, String addressProofNumber, String identityProofType, String identityProofNumber, String vehicleNumber,
                                Address presentAddress, Address permanentAddress, AccountDetails accountDetails, VehicleDetails vehicleDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.fatherName = fatherName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.addressProofType = addressProofType;
        this.addressProofNumber = addressProofNumber;
        this.identityProofType = identityProofType;
        this.identityProofNumber = identityProofNumber;
        this.vehicleNumber = vehicleNumber;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
        this.accountDetails = accountDetails;
        this.vehicleDetails = vehicleDetails;
    }

    // Getters and Setters
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

    public String getAddressProofType() {
        return addressProofType;
    }

    public void setAddressProofType(String addressProofType) {
        this.addressProofType = addressProofType;
    }

    public String getAddressProofNumber() {
        return addressProofNumber;
    }

    public void setAddressProofNumber(String addressProofNumber) {
        this.addressProofNumber = addressProofNumber;
    }

    public String getIdentityProofType() {
        return identityProofType;
    }

    public void setIdentityProofType(String identityProofType) {
        this.identityProofType = identityProofType;
    }

    public String getIdentityProofNumber() {
        return identityProofNumber;
    }

    public void setIdentityProofNumber(String identityProofNumber) {
        this.identityProofNumber = identityProofNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Address getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(Address presentAddress) {
        this.presentAddress = presentAddress;
    }

    public Address getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(Address permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}
