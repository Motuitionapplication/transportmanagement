package com.transportation.app.binding;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class PaymentTable {

	@Id
	@JsonIgnore
	@Column(name = "transaction_id", updatable = false, nullable = false)
	private UUID transactionId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "driver_Username", referencedColumnName = "username")
	private DriverParameter driver;

	private LocalDate date;

	@Schema(description = "Pickup area pin code", example = "751003")
	@Column(name = "pickup_pin")
	private String pickupPin;

	@Schema(description = "Drop area pin code", example = "110001")
	@Column(name = "drop_pin")
	private String dropPin;

	@Schema(description = "City of travel", example = "Delhi")
	private String city;

	@Schema(description = "Distance between pickup and drop in kilometers", example = "12.5")
	private Double distance;

	@Schema(description = "Mode of payment", example = "UPI")
	private String paymentMode;

	@Schema(description = "Payment amount in INR", example = "350.75")
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal amount;

	private String driverName;
	
	private String vehicleNumber;

	// Getters and setters
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public DriverParameter getDriver() {
		return driver;
	}

	public void setDriver(DriverParameter driver) {
		this.driver = driver;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public String getPickupPin() {
		return pickupPin;
	}

	public void setPickupPin(String pickupPin) {
		this.pickupPin = pickupPin;
	}

	public String getDropPin() {
		return dropPin;
	}

	public void setDropPin(String dropPin) {
		this.dropPin = dropPin;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	
}
