package com.transportmanagementfrontend;

public class CustomerLoginResponse {
    private boolean success;
    private String status;
    private CustomerParameter user; // Include UserParameter object
    private String role;
  //  private String token; // If the backend returns a JWT token

    // Constructor
    public CustomerLoginResponse(boolean success, String status, CustomerParameter user) {
        this.success = success;
        this.status = status;
        this.user = user;
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

    public CustomerParameter getUser() {
        return user;
    }

    // Setters (if needed)
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(CustomerParameter user) {
        this.user = user;
    }
}
