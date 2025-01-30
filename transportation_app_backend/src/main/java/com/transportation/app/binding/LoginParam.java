package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;




/**
 * This transport object.
 */
public class LoginParam {

	@Schema(description = "Username used for login", example = "vibek123")
	private String username;

	@Schema(description = "Password for the user account", example = "password123")
	private String password;

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
