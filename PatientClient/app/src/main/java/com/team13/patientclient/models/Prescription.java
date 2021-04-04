package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Prescription {
    @SerializedName("medicine_list")
    ArrayList<DrugDetail> drugList;

    @SerializedName("note")
    String note;

    @SerializedName("symptoms")
    String symptom;

    @SerializedName("diagnosis")
    String diagnose;

    @SerializedName("doctor")
    Doctor doctor;

    @SerializedName("date")
    String date;

    public class Doctor {
        @SerializedName("name")
        String doctorName;
    }
}
