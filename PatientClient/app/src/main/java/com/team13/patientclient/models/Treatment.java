package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;
import com.team13.patientclient.Store;

import java.io.Serializable;
import java.util.ArrayList;

public class Treatment implements Serializable{
    @SerializedName("_id")
    String id;

    @SerializedName("doctor")
    Doctor doctor;

    @SerializedName("service")
    ServicePack servicePack;

    @SerializedName("time")
    String time;

    @SerializedName("date")
    String date;

    @SerializedName("note")
    String note;

    @SerializedName("prescription")
    Prescription prescription;

    public Treatment(Appointment appointment, Prescription prescription) {
        this.doctor = appointment.getDoctor();
        this.servicePack = appointment.getService();
        this.time = appointment.getTime();
        this.date = appointment.getDate();
        this.note = appointment.getNote();
        this.prescription = prescription;
    }


    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public String getServicePack() {
        return servicePack.getName();
    }

    public String getDate() {
        return date;
    }

    public String getDoctorName() {
        return doctor.getDoctorName();
    }
    public String getDoctorId() {
        return doctor.getId();
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
