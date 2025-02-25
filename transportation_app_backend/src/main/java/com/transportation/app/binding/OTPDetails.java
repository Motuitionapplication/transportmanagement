package com.transportation.app.binding;
import java.time.LocalDateTime;

public class OTPDetails {
	 private long mobileNumber;
	    private int otp;
	    private LocalDateTime generatedTime;
	    private LocalDateTime expiryTime;

	    // Constructors
	    public OTPDetails() {}

	    public OTPDetails(long mobileNumber, int otp, LocalDateTime generatedTime, LocalDateTime expiryTime) {
	        this.mobileNumber = mobileNumber;
	        this.otp = otp;
	        this.generatedTime = generatedTime;
	        this.expiryTime = expiryTime;
	    }

	    // Getters and Setters
	    public long getMobileNumber() {
	        return mobileNumber;
	    }

	    public void setMobileNumber(long mobileNumber) {
	        this.mobileNumber = mobileNumber;
	    }

	    public int getOtp() {
	        return otp;
	    }

	    public void setOtp(int otp) {
	        this.otp = otp;
	    }

	    public LocalDateTime getGeneratedTime() {
	        return generatedTime;
	    }

	    public void setGeneratedTime(LocalDateTime generatedTime) {
	        this.generatedTime = generatedTime;
	    }

	    public LocalDateTime getExpiryTime() {
	        return expiryTime;
	    }

	    public void setExpiryTime(LocalDateTime expiryTime) {
	        this.expiryTime = expiryTime;
	    }

	    @Override
	    public String toString() {
	        return "OTPDetails{" +
	                "mobileNumber=" + mobileNumber +
	                ", otp=" + otp +
	                ", generatedTime=" + generatedTime +
	                ", expiryTime=" + expiryTime +
	                '}';
	    }

}
