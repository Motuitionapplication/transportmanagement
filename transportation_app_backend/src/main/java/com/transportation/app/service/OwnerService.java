package com.transportation.app.service;

import com.transportation.app.binding.LoginParamOwner;
import com.transportation.app.binding.LoginResponseOwner;
import com.transportation.app.binding.OwnerParameter;

public interface OwnerService {

	// Creates a new owner
	public String createOrUpdateOwner(OwnerParameter ownerParameter);

	// Checks login credentials
	public LoginResponseOwner checkLogin(LoginParamOwner loginParamOwner);

	// Retrieves owner details by username
	public OwnerParameter findByUsername(String username);

	/**
	 * Updates an existing owner. If the owner with the given ID doesn't exist, the
	 * implementation should return "Owner not found". The controller will then call
	 * createOwner() for upsert behavior.
	 */
	public String updateOwner(OwnerParameter ownerParameter);

	// Deletes an owner by ID
	public String deleteOwner(int ownerId);
}
