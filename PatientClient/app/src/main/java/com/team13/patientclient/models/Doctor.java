package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {
    @SerializedName("_id")
    String id;

    @SerializedName("name")
    String doctorName;

    public Doctor(String id, String doctorName) {
        this.id = id;
        this.doctorName = doctorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
