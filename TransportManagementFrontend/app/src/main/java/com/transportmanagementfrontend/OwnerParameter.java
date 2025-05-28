package com.transportmanagementfrontend;

public class OwnerParameter {
    private Integer id;

    // Personal Info
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String fatherName;
    private String email;

    // Present Address Info
    private String presProofType;
    private String presProofNumber;
    private String presAt;
    private String presPo;
    private String presTown;
    private String presPs;
    private String presDist;
    private String presState;
    private String presPin;
    private String presMob;
    private String presType;

    // Permanent Address Info
    private String permProofType;
    private String permProofNumber;
    private String permAt;
    private String permPo;
    private String permTown;
    private String permPs;
    private String permDist;
    private String permState;
    private String permPin;
    private String permMob;
    private String permType;

    // Account Info
    private String accountNumber;
    private String branchName;
    private String ifscCode;
    private String branchAddress;
    private String upiNumber;
    private String gst;

    // Vehicle Info
    private String vehicleNumber;
    private String vehicleType;
    private String chassisNumber;
    private String insurancePaper;
    private String fitnessCert;
    private String permit;
    private String pollutionCert;
    private String rc;
    private String capacity;

    // No-argument constructor (needed for Gson/Retrofit)
    public OwnerParameter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Full constructor
    public OwnerParameter(String firstName, String lastName, String phone, String username, String fatherName, String email,
                 String presProofType, String presProofNumber, String presAt, String presPo, String presTown,
                 String presPs, String presDist, String presState, String presPin, String presMob, String presType,
                 String permProofType, String permProofNumber, String permAt, String permPo, String permTown,
                 String permPs, String permDist, String permState, String permPin, String permMob, String permType,
                 String accountNumber, String branchName, String ifscCode, String branchAddress, String upiNumber,
                 String gst, String vehicleNumber, String vehicleType, String chassisNumber, String insurancePaper,
                 String fitnessCert, String permit, String pollutionCert, String rc, String capacity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
        this.fatherName = fatherName;
        this.email = email;
        this.presProofType = presProofType;
        this.presProofNumber = presProofNumber;
        this.presAt = presAt;
        this.presPo = presPo;
        this.presTown = presTown;
        this.presPs = presPs;
        this.presDist = presDist;
        this.presState = presState;
        this.presPin = presPin;
        this.presMob = presMob;
        this.presType = presType;
        this.permProofType = permProofType;
        this.permProofNumber = permProofNumber;
        this.permAt = permAt;
        this.permPo = permPo;
        this.permTown = permTown;
        this.permPs = permPs;
        this.permDist = permDist;
        this.permState = permState;
        this.permPin = permPin;
        this.permMob = permMob;
        this.permType = permType;
        this.accountNumber = accountNumber;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
        this.branchAddress = branchAddress;
        this.upiNumber = upiNumber;
        this.gst = gst;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.chassisNumber = chassisNumber;
        this.insurancePaper = insurancePaper;
        this.fitnessCert = fitnessCert;
        this.permit = permit;
        this.pollutionCert = pollutionCert;
        this.rc = rc;
        this.capacity = capacity;
    }

    // Getters and setters below (same as previous)

    // Personal Info
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Present Address Info
    public String getPresProofType() { return presProofType; }
    public void setPresProofType(String presProofType) { this.presProofType = presProofType; }

    public String getPresProofNumber() { return presProofNumber; }
    public void setPresProofNumber(String presProofNumber) { this.presProofNumber = presProofNumber; }

    public String getPresAt() { return presAt; }
    public void setPresAt(String presAt) { this.presAt = presAt; }

    public String getPresPo() { return presPo; }
    public void setPresPo(String presPo) { this.presPo = presPo; }

    public String getPresTown() { return presTown; }
    public void setPresTown(String presTown) { this.presTown = presTown; }

    public String getPresPs() { return presPs; }
    public void setPresPs(String presPs) { this.presPs = presPs; }

    public String getPresDist() { return presDist; }
    public void setPresDist(String presDist) { this.presDist = presDist; }

    public String getPresState() { return presState; }
    public void setPresState(String presState) { this.presState = presState; }

    public String getPresPin() { return presPin; }
    public void setPresPin(String presPin) { this.presPin = presPin; }

    public String getPresMob() { return presMob; }
    public void setPresMob(String presMob) { this.presMob = presMob; }

    public String getPresType() { return presType; }
    public void setPresType(String presType) { this.presType = presType; }

    // Permanent Address Info
    public String getPermProofType() { return permProofType; }
    public void setPermProofType(String permProofType) { this.permProofType = permProofType; }

    public String getPermProofNumber() { return permProofNumber; }
    public void setPermProofNumber(String permProofNumber) { this.permProofNumber = permProofNumber; }

    public String getPermAt() { return permAt; }
    public void setPermAt(String permAt) { this.permAt = permAt; }

    public String getPermPo() { return permPo; }
    public void setPermPo(String permPo) { this.permPo = permPo; }

    public String getPermTown() { return permTown; }
    public void setPermTown(String permTown) { this.permTown = permTown; }

    public String getPermPs() { return permPs; }
    public void setPermPs(String permPs) { this.permPs = permPs; }

    public String getPermDist() { return permDist; }
    public void setPermDist(String permDist) { this.permDist = permDist; }

    public String getPermState() { return permState; }
    public void setPermState(String permState) { this.permState = permState; }

    public String getPermPin() { return permPin; }
    public void setPermPin(String permPin) { this.permPin = permPin; }

    public String getPermMob() { return permMob; }
    public void setPermMob(String permMob) { this.permMob = permMob; }

    public String getPermType() { return permType; }
    public void setPermType(String permType) { this.permType = permType; }

    // Account Info
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getBranchAddress() { return branchAddress; }
    public void setBranchAddress(String branchAddress) { this.branchAddress = branchAddress; }

    public String getUpiNumber() { return upiNumber; }
    public void setUpiNumber(String upiNumber) { this.upiNumber = upiNumber; }

    public String getGst() { return gst; }
    public void setGst(String gst) { this.gst = gst; }

    // Vehicle Info
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getChassisNumber() { return chassisNumber; }
    public void setChassisNumber(String chassisNumber) { this.chassisNumber = chassisNumber; }

    public String getInsurancePaper() { return insurancePaper; }
    public void setInsurancePaper(String insurancePaper) { this.insurancePaper = insurancePaper; }

    public String getFitnessCert() { return fitnessCert; }
    public void setFitnessCert(String fitnessCert) { this.fitnessCert = fitnessCert; }

    public String getPermit() { return permit; }
    public void setPermit(String permit) { this.permit = permit; }

    public String getPollutionCert() { return pollutionCert; }
    public void setPollutionCert(String pollutionCert) { this.pollutionCert = pollutionCert; }

    public String getRc() { return rc; }
    public void setRc(String rc) { this.rc = rc; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
}
