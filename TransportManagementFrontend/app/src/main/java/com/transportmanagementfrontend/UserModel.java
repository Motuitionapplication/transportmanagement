package com.transportmanagementfrontend;

public class UserModel {
        private Long id;
        private String username;
        private String role;
        private boolean approved;

        // Getters and Setters
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getRole() { return role; }
        public boolean isApproved() { return approved; }

        public void setId(Long id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
        public void setRole(String role) { this.role = role; }
        public void setApproved(boolean approved) { this.approved = approved; }
    }


