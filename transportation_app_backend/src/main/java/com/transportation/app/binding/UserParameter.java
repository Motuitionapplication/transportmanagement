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

	@Schema(description = "Gender of the user", example = "Male")
	private String gender;

	@Schema(description = "Residential address of the user", example = "123 Main St, Springfield")
	private String address;

	@Schema(description = "Username used for login", example = "vibek123")
	private String username;

	@Schema(description = "Password for the user account", example = "password123")
	private String password;

	public UserParameter() {
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
}
