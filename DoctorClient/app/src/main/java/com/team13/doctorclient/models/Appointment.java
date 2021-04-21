package com.team13.doctorclient.models;


import java.io.Serializable;

public class Appointment implements Serializable {
    String id;

    String patientId;

    ServicePack service;

    Doctor doctor;

    String date;

    String time;

    String note;

    String status;

    public Appointment(String patientId, ServicePack service, String note, String date, String time, String status){
        this.doctor = new Doctor("6064131892cd230c287d5bd4", "");
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.note = note;
        this.service = service;
        this.status = status;
    }

    public Appointment(){
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public ServicePack getService() {
        return service;
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
}
