package com.transportmanagementfrontend;

public class DriverLoginRequest {
    private String username;
    private String password;

    public DriverLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
