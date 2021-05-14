package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

public class IdResponse {
    @SerializedName("_id")
    String id;

    public String getId() {
        return id;
    }
}
