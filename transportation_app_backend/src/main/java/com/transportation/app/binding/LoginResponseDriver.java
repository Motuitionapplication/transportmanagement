package com.transportation.app.binding;

import java.util.Optional;

public class LoginResponseDriver {
	
	private boolean success;

	private String status;

	private Optional<DriverParameter> driver;

	public Optional<DriverParameter> getDriver() {
		return driver;
	}

	public void setDriver(Optional<DriverParameter> driver2) {
		this.driver = driver2;
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

	public void save(LoginParamDriver loginParamDriver) {
		// TODO Auto-generated method stub

	}

}
