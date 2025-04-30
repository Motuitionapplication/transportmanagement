package com.transportmanagementfrontend;

public class VehicleDetails {
    private String vehicleType;
    private String chassisNumber;
    private String insurancePaper;
    private String fitnessCert;
    private String permit;
    private String pollutionCert;
    private String rc;
    private String capacity;

    public VehicleDetails(String vehicleType,
                          String chassisNumber,
                          String insurancePaper,
                          String fitnessCert,
                          String permit,
                          String pollutionCert,
                          String rc,
                          String capacity) {
        this.vehicleType = vehicleType;
        this.chassisNumber = chassisNumber;
        this.insurancePaper = insurancePaper;
        this.fitnessCert = fitnessCert;
        this.permit = permit;
        this.pollutionCert = pollutionCert;
        this.rc = rc;
        this.capacity = capacity;
    }
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getInsurancePaper() {
        return insurancePaper;
    }

    public void setInsurancePaper(String insurancePaper) {
        this.insurancePaper = insurancePaper;
    }

    public String getFitnessCert() {
        return fitnessCert;
    }

    public void setFitnessCert(String fitnessCert) {
        this.fitnessCert = fitnessCert;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getPollutionCert() {
        return pollutionCert;
    }

    public void setPollutionCert(String pollutionCert) {
        this.pollutionCert = pollutionCert;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
