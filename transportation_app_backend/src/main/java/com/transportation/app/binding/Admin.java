package com.transportation.app.binding;

import com.transportation.app.constants.UserRoles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {

	@Id
	@Schema(description = "Username used for login", example = "vibek123")
	private String username;

	@Schema(description = "Identifies the First Name", example = "Vibek")
	private String firstName;

	@Schema(description = "Identifies the Last Name", example = "Kumar")
	private String lastName;

	@Schema(description = "City of travel", example = "Delhi")
	private String city;

	@Schema(description = "Password for the user account", example = "password123")
	private String password;

	@Schema(description = "Specifies the type of user (A=Admin, C=Customer, O=Owner, D=Driver)", example = UserRoles.ADMIN)
	private String role;


	@Schema(description = "Mobile number of the user", example = "9938325746")
	private String phone;

	public Admin() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	
	
}
