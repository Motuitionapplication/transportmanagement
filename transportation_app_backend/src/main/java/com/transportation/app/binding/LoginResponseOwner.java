package com.transportation.app.binding;

import java.util.Optional;

public class LoginResponseOwner {

	private boolean success;

	private String status;

	private Optional<OwnerParameter> owner;

	public Optional<OwnerParameter> getOwner() {
		return owner;
	}

	public void setOwner(Optional<OwnerParameter> owner2) {
		this.owner = owner2;
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

	public void save(LoginParamOwner loginParamOwner) {
		// TODO Auto-generated method stub

	}

}
