package com.transportation.app.binding;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "OWNER_REG_DETAILS")
public class OwnerParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Schema(description = "First name of the owner", example = "Vibek")
	private String firstName;

	@Schema(description = "Last name of the owner", example = "Kumar")
	private String lastName;

	@Schema(description = "Phone number of the owner", example = "9876543210")
	private String phone;

	@Schema(description = "Username for login", example = "vibek123")
	private String username;

	@Schema(description = "Password for login", example = "password@123")
	private String password;

	@Schema(description = "Father's name of the owner", example = "Ramesh Kumar")
	private String fatherName;

	@Schema(description = "Vehicle number", example = "BR01AB1234")
	private String vehicleNumber;

	@Schema(description = "Selected role", example = "OWNER")
	private String role;

	@Schema(description = "Email address", example = "vibek@example.com")
	private String email;

	@Version
	private Integer version;

	private String addressProofType;
	private String addressProofNumber;
	private boolean addressProofVerified;

	private String identityProofType;
	private String identityProofNumber;
	private boolean identityProofVerified;

	@Embedded
	private VehicleDetails vehicleDetails;

	@Embedded
	private AccountDetails accountDetails;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "at", column = @Column(name = "present_at")),
		@AttributeOverride(name = "po", column = @Column(name = "present_po")),
		@AttributeOverride(name = "town", column = @Column(name = "present_town")),
		@AttributeOverride(name = "ps", column = @Column(name = "present_ps")),
		@AttributeOverride(name = "dist", column = @Column(name = "present_dist")),
		@AttributeOverride(name = "state", column = @Column(name = "present_state")),
		@AttributeOverride(name = "pin", column = @Column(name = "present_pin")),
		@AttributeOverride(name = "mob", column = @Column(name = "present_mob")),
		@AttributeOverride(name = "type", column = @Column(name = "present_type"))
	})
	private Address presentAddress;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "at", column = @Column(name = "permanent_at")),
		@AttributeOverride(name = "po", column = @Column(name = "permanent_po")),
		@AttributeOverride(name = "town", column = @Column(name = "permanent_town")),
		@AttributeOverride(name = "ps", column = @Column(name = "permanent_ps")),
		@AttributeOverride(name = "dist", column = @Column(name = "permanent_dist")),
		@AttributeOverride(name = "state", column = @Column(name = "permanent_state")),
		@AttributeOverride(name = "pin", column = @Column(name = "permanent_pin")),
		@AttributeOverride(name = "mob", column = @Column(name = "permanent_mob")),
		@AttributeOverride(name = "type", column = @Column(name = "permanent_type"))
	})
	private Address permanentAddress;

	// ✅ RELATIONSHIP: One Owner to Many Drivers
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<DriverParameter> drivers;

	// ---------------- Getters & Setters ----------------

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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getAddressProofType() {
		return addressProofType;
	}

	public void setAddressProofType(String addressProofType) {
		this.addressProofType = addressProofType;
	}

	public String getAddressProofNumber() {
		return addressProofNumber;
	}

	public void setAddressProofNumber(String addressProofNumber) {
		this.addressProofNumber = addressProofNumber;
	}

	public boolean isAddressProofVerified() {
		return addressProofVerified;
	}

	public void setAddressProofVerified(boolean addressProofVerified) {
		this.addressProofVerified = addressProofVerified;
	}

	public String getIdentityProofType() {
		return identityProofType;
	}

	public void setIdentityProofType(String identityProofType) {
		this.identityProofType = identityProofType;
	}

	public String getIdentityProofNumber() {
		return identityProofNumber;
	}

	public void setIdentityProofNumber(String identityProofNumber) {
		this.identityProofNumber = identityProofNumber;
	}

	public boolean isIdentityProofVerified() {
		return identityProofVerified;
	}

	public void setIdentityProofVerified(boolean identityProofVerified) {
		this.identityProofVerified = identityProofVerified;
	}

	public VehicleDetails getVehicleDetails() {
		return vehicleDetails;
	}

	public void setVehicleDetails(VehicleDetails vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
	}

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

	public Address getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(Address presentAddress) {
		this.presentAddress = presentAddress;
	}

	public Address getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public List<DriverParameter> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<DriverParameter> drivers) {
		this.drivers = drivers;
	}
}
