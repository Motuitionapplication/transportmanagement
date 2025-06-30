package com.transportmanagementfrontend;

public class OwnerLoginRequest {
    private String username;
    private String password;

    public OwnerLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
