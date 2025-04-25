package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "DRIVER_REG_DETAILS")
public class DriverParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Driver's first name", example = "Rajesh")
    private String firstName;

    @Schema(description = "Driver's last name", example = "Kumar")
    private String lastName;

    @Schema(description = "Mobile number", example = "9876543210")
    private String phone;

    @Schema(description = "Username or User ID", example = "rajesh.kumar")
    private String username;

    @Schema(description = "Password", example = "Pass@123")
    private String password;

    @Schema(description = "Driving license number", example = "DL0420190012345")
    private String dlNumber;

    @Schema(description = "Type of driving license", example = "LMV")
    private String dlType;

    @Schema(description = "Email ID", example = "rajesh.kumar@example.com")
    private String email;

    @Schema(description = "Father or husband name", example = "Ramesh Kumar")
    private String fatherOrHusbandName;

    @Lob
    @Schema(description = "Passport size photo (compressed)", example = "Base64 encoded image bytes")
    private byte[] passportPhoto;

    @Schema(description = "Type of identity proof", example = "Aadhar")
    private String identityProofType;

    @Schema(description = "Path to uploaded identity proof file", example = "/uploads/docs/aadhar_101.pdf")
    private String identityProofFilePath;

    @Schema(description = "Blood group", example = "B+")
    private String bloodGroup;

    @Schema(description = "Path to uploaded insurance paper", example = "/uploads/docs/insurance_101.pdf")
    private String insurancePaperFilePath;

    @Schema(description = "Vehicle number assigned", example = "MH12AB1234")
    private String vehicleNumber;

    @Schema(description = "Role of the user", example = "Driver")
    private String selectedRole;

    public DriverParameter() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDlNumber() {
		return dlNumber;
	}

	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}

	public String getDlType() {
		return dlType;
	}

	public void setDlType(String dlType) {
		this.dlType = dlType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFatherOrHusbandName() {
		return fatherOrHusbandName;
	}

	public void setFatherOrHusbandName(String fatherOrHusbandName) {
		this.fatherOrHusbandName = fatherOrHusbandName;
	}

	public byte[] getPassportPhoto() {
		return passportPhoto;
	}

	public void setPassportPhoto(byte[] passportPhoto) {
		this.passportPhoto = passportPhoto;
	}

	public String getIdentityProofType() {
		return identityProofType;
	}

	public void setIdentityProofType(String identityProofType) {
		this.identityProofType = identityProofType;
	}

	public String getIdentityProofFilePath() {
		return identityProofFilePath;
	}

	public void setIdentityProofFilePath(String identityProofFilePath) {
		this.identityProofFilePath = identityProofFilePath;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getInsurancePaperFilePath() {
		return insurancePaperFilePath;
	}

	public void setInsurancePaperFilePath(String insurancePaperFilePath) {
		this.insurancePaperFilePath = insurancePaperFilePath;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}
    

    // Getters and Setters (same as before)

    // ... [getters and setters omitted for brevity] ...
    
}
