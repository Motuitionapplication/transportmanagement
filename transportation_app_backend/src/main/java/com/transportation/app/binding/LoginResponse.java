package com.transportation.app.binding;

import java.util.Optional;

public class LoginResponse {

	private boolean success;
	
	private String status;
    
	private Optional<UserParameter> user;
	
	public Optional<UserParameter> getUser() {
		return user;
	}

	public void setUser(Optional<UserParameter> user2) {
		this.user = user2;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void save(LoginParam loginParam) {
		// TODO Auto-generated method stub

	}

}
