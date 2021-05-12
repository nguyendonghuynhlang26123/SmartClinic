package com.team13.doctorclient.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appointment implements Serializable {
    @SerializedName("_id")
    @Expose(serialize =  false)
    String id;

    @SerializedName("patient")
    PatientModel patient;

    @SerializedName("doctor")
    Doctor doctor;

    @SerializedName("service")
    ServicePack service;

    @SerializedName("date")
    String date;

    @SerializedName("time")
    String time;

    @SerializedName("note")
    String note;

    @SerializedName("status")
    String status;

    public Appointment(String patientName, ServicePack service, String note, String date, String time, String status){
        patient = new PatientModel(patientName);
        this.date = date;
        this.time = time;
        this.note = note;
        this.service = service;
        this.status = status;
    }

    public Appointment(){
    }

    public String getPatientId() {
        return patient.getId();
    }

    public ServicePack getService() {
        return service;
    }

    public void setService(ServicePack service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId(){
        return doctor.getId();
    }

    public PatientModel getPatient() {
        return patient;
    }
}
