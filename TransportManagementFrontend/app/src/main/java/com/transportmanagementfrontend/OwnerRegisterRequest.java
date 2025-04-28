package com.transportmanagementfrontend;

public class OwnerRegisterRequest {
    // Personal
    private String firstName;
    private String lastName;
    private String phone;
    private String fatherName;
    private String email;
    private String username;
    private String password;
    private String role;
    private String addressProofType;
    private String addressProofNumber;
    private String identityProofType;
    private String identityProofNumber;

    // Present Address
    private String presAt;
    private String presPo;
    private String presTown;
    private String presPs;
    private String presDist;
    private String presState;
    private String presPin;
    private String presMob;
    private String presType;

    // Permanent Address
    private String permAt;
    private String permPo;
    private String permTown;
    private String permPs;
    private String permDist;
    private String permState;
    private String permPin;
    private String permMob;
    private String permType;

    // Account (all optional)
    private String accountNumber;
    private String branchName;
    private String ifscCode;
    private String branchAddress;
    private String upiNumber;
    private String gst;

    // Vehicle
    private String vehicleNumber;
    private String vehicleType;
    private String chassisNumber;
    private String insurancePaper;
    private String fitnessCert;
    private String permit;
    private String pollutionCert;
    private String rc;
    private String capacity;

    public OwnerRegisterRequest(
            // personal
            String firstName, String lastName, String phone, String fatherName,
            String email, String username, String password, String role,  String addressProofType,
            String addressProofNumber,
            String identityProofType,
            String identityProofNumber,
            // present address
             String presAt, String presPo,
            String presTown, String presPs, String presDist, String presState,
            String presPin, String presMob, String presType,
            // permanent address
            String permAt, String permPo,
            String permTown, String permPs, String permDist, String permState,
            String permPin, String permMob, String permType,
            // account
            String accountNumber, String branchName, String ifscCode,
            String branchAddress, String upiNumber, String gst,
            // vehicle
            String vehicleNumber, String vehicleType, String chassisNumber,
            String insurancePaper, String fitnessCert, String permit,
            String pollutionCert, String rc, String capacity
    ) {
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.phone           = phone;
        this.fatherName      = fatherName;
        this.email           = email;
        this.username        = username;
        this.password        = password;
        this.role            = role;
        this.addressProofType = addressProofType;
        this.addressProofNumber = addressProofNumber;
        this.identityProofType = identityProofType;
        this.identityProofNumber = identityProofNumber;


        this.presAt          = presAt;
        this.presPo          = presPo;
        this.presTown        = presTown;
        this.presPs          = presPs;
        this.presDist        = presDist;
        this.presState       = presState;
        this.presPin         = presPin;
        this.presMob         = presMob;
        this.presType        = presType;


        this.permAt          = permAt;
        this.permPo          = permPo;
        this.permTown        = permTown;
        this.permPs          = permPs;
        this.permDist        = permDist;
        this.permState       = permState;
        this.permPin         = permPin;
        this.permMob         = permMob;
        this.permType        = permType;

        this.accountNumber   = accountNumber;
        this.branchName      = branchName;
        this.ifscCode        = ifscCode;
        this.branchAddress   = branchAddress;
        this.upiNumber       = upiNumber;
        this.gst             = gst;

        this.vehicleNumber   = vehicleNumber;
        this.vehicleType     = vehicleType;
        this.chassisNumber   = chassisNumber;
        this.insurancePaper  = insurancePaper;
        this.fitnessCert     = fitnessCert;
        this.permit          = permit;
        this.pollutionCert   = pollutionCert;
        this.rc              = rc;
        this.capacity        = capacity;
    }

    // Getters and setters omitted for brevity—but you can generate them via your IDE.
}
