package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}

