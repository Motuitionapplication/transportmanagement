package com.transportmanagementfrontend;

import com.google.gson.annotations.SerializedName;

public class DriverRegisterResponse {
    @SerializedName("message")
    private String message;

    public DriverRegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
