package com.transportmanagementfrontend;

public class AccountDetails {
    private String accountNumber;
    private String branchName;
    private String ifscCode;
    private String branchAddress;
    private String upiNumber;
    private String gst;

    public AccountDetails(String accountNumber,
                          String branchName,
                          String ifscCode,
                          String branchAddress,
                          String upiNumber,
                          String gst) {
        this.accountNumber = accountNumber;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
        this.branchAddress = branchAddress;
        this.upiNumber = upiNumber;
        this.gst = gst;
    }

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
}
