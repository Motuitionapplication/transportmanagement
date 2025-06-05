package com.transportmanagementfrontend;

public class DriverRegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dlNumber;
    private String vehicleNumber;
    private String username;
    private String password;
    private String role;
    private OwnerParameter owner;

    public DriverRegisterRequest() {}

    public DriverRegisterRequest(String firstName, String lastName, String phone, String email, String dlNumber,
                                 String vehicleNumber, String username, String password, String role, String owner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dlNumber = dlNumber;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.password = password;
        this.role = role;
        this.owner = new OwnerParameter(owner); // Wrap inside Owner object
    }

    public static class OwnerParameter {
        private String username;

        public OwnerParameter(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getDlNumber() { return dlNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setDlNumber(String dlNumber) { this.dlNumber = dlNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
