package com.transportation.app.binding;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Schema(description = "House or location name", example = "H.No. 12, Shanti Nagar")
    private String at;

    @Schema(description = "Post Office name", example = "Kankarbagh")
    private String po;

    @Schema(description = "Town or city name", example = "Patna")
    private String town;

    @Schema(description = "Police station of the area", example = "Kankarbagh PS")
    private String ps;

    @Schema(description = "District name", example = "Patna")
    private String dist;

    @Schema(description = "State name", example = "Bihar")
    private String state;

    @Schema(description = "Postal code / PIN code", example = "800020")
    private String pin;

    @Schema(description = "Mobile number associated with this address", example = "9876543210")
    private String mob;

    @Schema(description = "Type of address (e.g., Home, Office)", example = "Home")
    private String type;

    // Getters and Setters
    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Method to update from another Address object
    public void updateFrom(Address newAddress) {
        if (newAddress.getAt() != null) this.at = newAddress.getAt();
        if (newAddress.getPo() != null) this.po = newAddress.getPo();
        if (newAddress.getTown() != null) this.town = newAddress.getTown();
        if (newAddress.getPs() != null) this.ps = newAddress.getPs();
        if (newAddress.getDist() != null) this.dist = newAddress.getDist();
        if (newAddress.getState() != null) this.state = newAddress.getState();
        if (newAddress.getPin() != null) this.pin = newAddress.getPin();
        if (newAddress.getMob() != null) this.mob = newAddress.getMob();
        if (newAddress.getType() != null) this.type = newAddress.getType();
    }
}
