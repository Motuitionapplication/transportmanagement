package com.transportmanagementfrontend;

public class AdminRequest {
        private String username;
        private String password;

        public AdminRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters and setters (optional if using Gson)
        public String getUsername() { return username; }
        public String getPassword() { return password; }

        public void setUsername(String username) { this.username = username; }
        public void setPassword(String password) { this.password = password; }
    }


