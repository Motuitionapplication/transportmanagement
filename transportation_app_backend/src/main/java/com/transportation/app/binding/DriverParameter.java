package com.transportation.app.binding;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DRIVER_REG_DETAILS")
public class DriverParameter {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "owner_username", referencedColumnName = "username")
	@JsonBackReference // This should match the FK column in your DB table
	private OwnerParameter owner;

	
	@OneToMany(mappedBy = "fk_driver", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<PaymentTable> payments ;
	
	
	

	@Schema(description = "Driver's first name", example = "Suresh")
	private String firstName;

	@Schema(description = "Driver's last name", example = "Naik")
	private String lastName;

	@Schema(description = "Mobile number", example = "9123456789")
	private String phone;

	@Schema(description = "Username or User ID", example = "suresh.naik95")
	@Column(unique = true, nullable = false)
	private String username;

	@Schema(description = "Password", example = "Drive@2025")
	private String password;

	@Schema(description = "Driving license number", example = "OD1420230098765")
	private String dlNumber;

	@Schema(description = "Type of driving license", example = "HMV")
	private String dlType;

	@Schema(description = "Email ID", example = "suresh.naik95@gmail.com")
	private String email;

	@Schema(description = "Father or husband name", example = "Ganesh Naik")
	private String fatherOrHusbandName;

	@Lob
	@Schema(description = "Passport size photo (Base64 encoded)", example = "data:image/jpeg;base64,/9j/4AAQSkZJRgABA...")
	private String passportPhoto;

	@Schema(description = "Type of identity proof", example = "Voter ID")
	private String identityProofType;

	@Schema(description = "Path to uploaded identity proof file", example = "/documents/idproofs/voterid_suresh.pdf")
	private String identityProofFilePath;

	@Schema(description = "Blood group", example = "O+")
	private String bloodGroup;

	@Schema(description = "Path to uploaded insurance paper", example = "/documents/insurance/insurance_suresh.pdf")
	private String insurancePaperFilePath;

	@Schema(description = "Vehicle number assigned", example = "OD05AZ7896")
	private String vehicleNumber;

	@Schema(description = "Role of the user", example = "Driver")
	private String role;

	@Schema(description = "Type of the vehicle", example = "Mini Truck")
	private String vehicleType;

	@Schema(description = "Availability status of the driver", example = "Unavailable")
	private String driverAvelablilityStatus;

	@Schema(description = "Driver's current city", example = "Bhubaneswar")
	private String city;


	public String getCity() {
	    return city;
	}

	public void setCity(String city) {
	    this.city = city;
	}


	public DriverParameter() {
	}

	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getPassportPhoto() {
		return passportPhoto;
	}

	public void setPassportPhoto(String passportPhoto) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public OwnerParameter getOwner() {
		return owner;
	}

	public void setOwner(OwnerParameter owner) {
		this.owner = owner;
	}


	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<PaymentTable> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentTable> payments) {
		this.payments = payments;
	}

	public String getDriverAvelablilityStatus() {
		return driverAvelablilityStatus;
	}

	public void setDriverAvelablilityStatus(String driverAvelablilityStatus) {
		this.driverAvelablilityStatus = driverAvelablilityStatus;
	}

// (same as before)

	// ... [getters and setters omitted for brevity] ...

}
