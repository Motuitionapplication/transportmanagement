package com.transportmanagementfrontend;

import java.io.Serializable;

public class OrderParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private String vehicleNumber;
    private String pickupLocation;
    private String dropLocation;
    private double distance;      // in kilometers
    private double price;         // in your currency unit
    private boolean available;

    public OrderParameter(String orderId,
                          String vehicleNumber,
                          String pickupLocation,
                          String dropLocation,
                          double distance,
                          double price,
                          boolean available) {
        this.orderId = orderId;
        this.vehicleNumber = vehicleNumber;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.price = price;
        this.available = available;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public double getDistance() {
        return distance;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    // (Optional) setters, if you need mutability
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
