package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VehicleDetails {

   

    @Schema(description = "Chassis number of the vehicle", example = "MH12AB1234XYZ")
    @Column(name = "chassis_number")
    private String chassisNumber;

    @Schema(description = "Whether the vehicle is auto-verified", example = "true")
    @Column(name = "is_auto_verified")
    private boolean isAutoVerified;

    @Schema(description = "Base64-encoded insurance paper document", example = "base64encodedstring==")
    @Column(name = "insurance_paper")
    private String insurancePaper;

    @Schema(description = "Base64-encoded fitness certificate", example = "base64encodedstring==")
    @Column(name = "fitness_certificate")
    private String fitnessCertificate;

    @Schema(description = "Base64-encoded vehicle permit", example = "base64encodedstring==")
    @Column(name = "permit")
    private String permit;

    @Schema(description = "Base64-encoded pollution certificate", example = "base64encodedstring==")
    @Column(name = "pollution_certificate")
    private String pollutionCertificate;

    @Schema(description = "Base64-encoded RC (Registration Certificate)", example = "base64encodedstring==")
    @Column(name = "rc")
    private String rc;

    @Schema(description = "Seating or load capacity of the vehicle", example = "20")
    @Column(name = "capacity")
    private int capacity;

    @Schema(description = "Type of vehicle (e.g., Truck, Bus, Auto)", example = "Truck")
    @Column(name = "vehicle_type")
    private String vehicleType;

    // Getters and Setters

  
    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public boolean isAutoVerified() {
        return isAutoVerified;
    }

    public void setAutoVerified(boolean isAutoVerified) {
        this.isAutoVerified = isAutoVerified;
    }

    public String getInsurancePaper() {
        return insurancePaper;
    }

    public void setInsurancePaper(String insurancePaper) {
        this.insurancePaper = insurancePaper;
    }

    public String getFitnessCertificate() {
        return fitnessCertificate;
    }

    public void setFitnessCertificate(String fitnessCertificate) {
        this.fitnessCertificate = fitnessCertificate;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getPollutionCertificate() {
        return pollutionCertificate;
    }

    public void setPollutionCertificate(String pollutionCertificate) {
        this.pollutionCertificate = pollutionCertificate;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Method to update values from another VehicleDetails object
    public void updateFrom(VehicleDetails newDetails) {
        if (newDetails.getChassisNumber() != null) this.chassisNumber = newDetails.getChassisNumber();
        if (newDetails.isAutoVerified()) this.isAutoVerified = newDetails.isAutoVerified();
        if (newDetails.getInsurancePaper() != null) this.insurancePaper = newDetails.getInsurancePaper();
        if (newDetails.getFitnessCertificate() != null) this.fitnessCertificate = newDetails.getFitnessCertificate();
        if (newDetails.getPermit() != null) this.permit = newDetails.getPermit();
        if (newDetails.getPollutionCertificate() != null) this.pollutionCertificate = newDetails.getPollutionCertificate();
        if (newDetails.getRc() != null) this.rc = newDetails.getRc();
        if (newDetails.getCapacity() != 0) this.capacity = newDetails.getCapacity();
        if (newDetails.getVehicleType() != null) this.vehicleType = newDetails.getVehicleType();
    }
}
