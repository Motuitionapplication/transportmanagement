package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;

@Embeddable
public class AccountDetails {

    @Schema(description = "Bank account number", example = "123456789012")
    private String accountNumber;

    @Schema(description = "Name of the bank branch", example = "SBI Main Branch")
    private String branchName;

    @Schema(description = "IFSC code of the branch", example = "SBIN0001234")
    private String ifscCode;

    @Schema(description = "Address of the bank branch", example = "123 Main St, Patna, Bihar")
    private String branchAddress;

    @Schema(description = "Base64 encoded passbook photo", example = "iVBORw0KGgoAAAANSUhEUgAA... (base64 string)")
    private String passbookPhoto;

    @Schema(description = "UPI or PhonePe number linked to account", example = "9876543210@ybl")
    private String upiNumber;

    @Schema(description = "GST number if available", example = "10ABCDE1234F1Z5")
    private String gst;

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getPassbookPhoto() {
        return passbookPhoto;
    }

    public void setPassbookPhoto(String passbookPhoto) {
        this.passbookPhoto = passbookPhoto;
    }

  
    public String getUpiNumber() {
		return upiNumber;
	}

	public void setUpiNumber(String upiNumber) {
		this.upiNumber = upiNumber;
	}

	public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    // Method to update only non-null values from another AccountDetails instance
    public void updateFrom(AccountDetails newDetails) {
        if (newDetails.getAccountNumber() != null) this.accountNumber = newDetails.getAccountNumber();
        if (newDetails.getBranchName() != null) this.branchName = newDetails.getBranchName();
        if (newDetails.getIfscCode() != null) this.ifscCode = newDetails.getIfscCode();
        if (newDetails.getBranchAddress() != null) this.branchAddress = newDetails.getBranchAddress();
        if (newDetails.getPassbookPhoto() != null) this.passbookPhoto = newDetails.getPassbookPhoto();
        if (newDetails.getUpiNumber() != null) this.upiNumber = newDetails.getUpiNumber();
        if (newDetails.getGst() != null) this.gst = newDetails.getGst();
    }
}
