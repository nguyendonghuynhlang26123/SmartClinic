package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

public class MedicalHistory {
    @SerializedName("appointment")
    Appointment appointment;

    @SerializedName("prescription")
    Prescription prescription;

    @SerializedName("status")
    String status;
}
