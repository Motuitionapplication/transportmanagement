package com.transportmanagementfrontend;

import com.google.gson.annotations.SerializedName;

public class CustomerRegisterResponse {
    @SerializedName("message")
    private String message;

    public CustomerRegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
