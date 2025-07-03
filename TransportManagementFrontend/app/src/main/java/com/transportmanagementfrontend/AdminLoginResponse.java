package com.transportmanagementfrontend;

public class AdminLoginResponse {
    private boolean success;
    private String status;
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    // ✅ Inner class to represent 'user' object from response
    public static class User {
        private String username;

        public String getUsername() {
            return username;
        }
    }
}
