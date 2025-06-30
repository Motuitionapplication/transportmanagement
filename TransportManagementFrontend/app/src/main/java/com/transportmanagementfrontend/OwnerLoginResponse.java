package com.transportmanagementfrontend;

public class OwnerLoginResponse {
    private boolean success;
    private String status;
    private OwnerParameter owner;
    private String role;

    // No-args constructor for Gson
    public OwnerLoginResponse() { }

    // (Optional) Convenience constructor
    public OwnerLoginResponse(boolean success, String status, OwnerParameter owner, String role) {
        this.success = success;
        this.status  = status;
        this.owner   = owner;
        this.role    = role;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public OwnerParameter getOwner() {
        return owner;
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

    public void setOwner(OwnerParameter owner) {
        this.owner = owner;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
