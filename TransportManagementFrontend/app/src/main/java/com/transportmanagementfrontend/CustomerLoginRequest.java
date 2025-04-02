package com.transportmanagementfrontend;

public class CustomerLoginRequest {
    private String username;
    private String password;

    public CustomerLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
