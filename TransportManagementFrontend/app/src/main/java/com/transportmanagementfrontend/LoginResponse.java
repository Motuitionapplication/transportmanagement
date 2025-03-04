package com.transportmanagementfrontend;

public class LoginResponse {
    private boolean success;
    private String status;
    private String role;
  //  private String token; // If the backend returns a JWT token

    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }
    public String getRole() {
        return role;
    }

//    public String getToken() {
//        return token;
//    }
}
