package com.transportmanagementfrontend;

public class Address {
    private String at;
    private String po;
    private String town;
    private String ps;
    private String dist;
    private String state;
    private String pin;
    private String mob;
    private String type;

    public Address(String at, String po, String town, String ps,
                   String dist, String state, String pin,
                   String mob, String type) {
        this.at = at;
        this.po = po;
        this.town = town;
        this.ps = ps;
        this.dist = dist;
        this.state = state;
        this.pin = pin;
        this.mob = mob;
        this.type = type;
    }

    // Generate getters and setters
}
