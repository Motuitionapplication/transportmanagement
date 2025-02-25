package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_REG_DETAILS")
public class UserParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Schema(description = "Identifies the First Name", example = "Vibek")
	private String firstName;

	@Schema(description = "Identifies the Last Name", example = "Kumar")
	private String lastName;

	@Schema(description = "Age of the user", example = "25")
	private int age;

	@Schema(description = "Residential address of the user", example = "123 Main St, Springfield")
	private String address;

	@Schema(description = "Username used for login", example = "vibek123")
	private String email;

	@Schema(description = "Password for the user account", example = "password123")
	private String password;

	@Schema(description = "Specifies the type of user (e.g., admin, regular)", example = "admin")
	private String role;

	@Schema(description = "Mobile number of the user", example = "9938325746")
	private String phone;

	@Schema(description = "OTP for verification", example = "123456")
	private String otp;

	// Default constructor
	public UserParameter() {
	}

	// Getters and Setters

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
}
