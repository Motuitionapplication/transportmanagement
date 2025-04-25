package com.transportmanagementfrontend;

import java.io.Serializable;

public class PaymentParameter implements Serializable {

    private String date;
    private String transactionId;
    private String pickup;
    private String drop;
    private String fare;
    private String driverName;
    private String vehicleNo;

    public PaymentParameter(String date, String transactionId, String pickup, String drop,
                            String fare, String driverName, String vehicleNo) {
        this.date = date;
        this.transactionId = transactionId;
        this.pickup = pickup;
        this.drop = drop;
        this.fare = fare;
        this.driverName = driverName;
        this.vehicleNo = vehicleNo;
    }

    public String getDate() { return date; }
    public String getTransactionId() { return transactionId; }
    public String getPickup() { return pickup; }
    public String getDrop() { return drop; }
    public String getFare() { return fare; }
    public String getDriverName() { return driverName; }
    public String getVehicleNo() { return vehicleNo; }
}
