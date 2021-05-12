package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Treatment implements Serializable {
    @SerializedName("_id")
    String id;

    @SerializedName("patient")
    PatientModel patient;

//    @SerializedName("doctor")
//    Doctor doctor;

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
//
//    public String getDoctorName() {
//        return doctor.getDoctorName();
//    }
//    public String getDoctorId() {
//        return doctor.getId();
//    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

//    public Doctor getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(Doctor doctor) {
//        this.doctor = doctor;
//    }

    public void setServicePack(ServicePack servicePack) {
        this.servicePack = servicePack;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

