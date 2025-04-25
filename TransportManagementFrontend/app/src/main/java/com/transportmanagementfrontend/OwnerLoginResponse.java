package com.transportmanagementfrontend;

public class OwnerLoginResponse {
    private boolean success;
    private String status;
    private OwnerParameter owner; // Include UserParameter object
    private String role;
  //  private String token; // If the backend returns a JWT token

    // Constructor
    public OwnerLoginResponse(boolean success, String status, OwnerParameter owner) {
        this.success = success;
        this.status = status;
        this.owner = owner;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }
    public String getRole() {
        return role;
    }

    public OwnerParameter getUser() {
        return owner;
    }

    // Setters (if needed)
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(CustomerParameter user) {
        this.owner = owner;
    }
}
