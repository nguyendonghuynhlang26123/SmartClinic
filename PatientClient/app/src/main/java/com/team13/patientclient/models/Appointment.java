package com.team13.patientclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appointment implements Serializable {
    @SerializedName("_id")
    String id;


    @SerializedName("service")
    ServicePack service;

    @SerializedName("doctor")
    Doctor doctor;

    @SerializedName("date")
    String date;

    @SerializedName("time")
    String time;

    @SerializedName("note")
    String note;

    @SerializedName("status")
    String status;


    public Appointment(){
    }

    public String getDoctorName(){return ((Doctor)doctor).doctorName;}

    public String getServiceName() {return (service).getName();}

    public String getServiceId() {
        return service.getId();
    }

    public ServicePack getService() {
        return (ServicePack) service;
    }

    public void setService(ServicePack service) {
        this.service = service;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus(){ return this.status;}
}
