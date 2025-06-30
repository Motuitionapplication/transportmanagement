package com.transportmanagementfrontend;

import com.google.gson.annotations.SerializedName;

public class OwnerRegisterResponse {
    @SerializedName("message")
    private String message;

    public OwnerRegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
