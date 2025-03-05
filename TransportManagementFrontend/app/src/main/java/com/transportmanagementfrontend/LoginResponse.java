package com.transportmanagementfrontend;

public class LoginResponse {
    private boolean success;
    private String status;
    private UserParameter user; // Include UserParameter object
    private String role;
  //  private String token; // If the backend returns a JWT token

    // Constructor
    public LoginResponse(boolean success, String status, UserParameter user) {
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

    public UserParameter getUser() {
        return user;
    }

    // Setters (if needed)
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(UserParameter user) {
        this.user = user;
    }
}
