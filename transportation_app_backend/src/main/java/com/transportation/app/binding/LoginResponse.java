package com.transportation.app.binding;

public class LoginResponse {

	private boolean success;
	
	private String status;

	
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
