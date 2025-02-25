package com.transportation.app.binding;
import java.time.LocalDateTime;

public class OTPDetails {
	 private long phone;
	    private int otp;
	    private LocalDateTime generatedTime;
	    private LocalDateTime expiryTime;

	    // Constructors
	    public OTPDetails() {}

	    public OTPDetails(long phone, int otp, LocalDateTime generatedTime, LocalDateTime expiryTime) {
	        this.phone = phone;
	        this.otp = otp;
	        this.generatedTime = generatedTime;
	        this.expiryTime = expiryTime;
	    }

	    // Getters and Setters


	    public int getOtp() {
	        return otp;
	    }

	    public long getPhone() {
			return phone;
		}

		public void setPhone(long phone) {
			this.phone = phone;
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
			return "OTPDetails [phone=" + phone + ", otp=" + otp + ", generatedTime=" + generatedTime + ", expiryTime="
					+ expiryTime + "]";
		}

	

}
