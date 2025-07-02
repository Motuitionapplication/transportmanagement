package com.transportmanagementfrontend;

public class Vehicle {
    private String vehicleNumber;
    private String status; // Available, Unavailable, Reserved
    private String ownerName;
    private String date;

    public Vehicle(String vehicleNumber, String status, String ownerName, String date) {
        this.vehicleNumber = vehicleNumber;
        this.status = status;
        this.ownerName = ownerName;
        this.date = date;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public String getStatus() { return status; }
    public String getOwnerName() { return ownerName; }
    public String getDate() { return date; }
}
