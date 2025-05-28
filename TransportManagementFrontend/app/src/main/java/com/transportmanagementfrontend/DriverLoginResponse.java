package com.transportmanagementfrontend;

public class DriverLoginResponse {
    private boolean success;
    private String status;
    private DriverParameter driver;
    private String role;

    // No-args constructor for Gson
    public DriverLoginResponse() { }

    // Convenience constructor
    public DriverLoginResponse(boolean success, String status, DriverParameter driver, String role) {
        this.success = success;
        this.status = status;
        this.driver = driver;
        this.role = role;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public DriverParameter getDriver() {
        return driver;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDriver(DriverParameter driver) {
        this.driver = driver;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
