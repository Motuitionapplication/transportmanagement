package com.transportmanagementfrontend;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentParameter implements Serializable {

    private String date;
    private String transactionId;
    private String pickupPin;
    private String dropPin;
    private String city;
    private double distance;
    private String paymentMode;
    private BigDecimal amount;

    public PaymentParameter(String date, String transactionId, String pickupPin, String dropPin,
                            String city, double distance, String paymentMode, BigDecimal amount) {
        this.date = date;
        this.transactionId = transactionId;
        this.pickupPin = pickupPin;
        this.dropPin = dropPin;
        this.city = city;
        this.distance = distance;
        this.paymentMode = paymentMode;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPickupPin() {
        return pickupPin;
    }

    public String getDropPin() {
        return dropPin;
    }

    public String getCity() {
        return city;
    }

    public double getDistance() {
        return distance;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
