package com.team13.doctorclient.models;


public class Appointment {
    String id;

    String patientId;


    String serviceId;

    String doctorId;

    String date;

    String time;

    String note;

    public Appointment(String patientId, String serviceId, String note, String date, String time){
        this.patientId = patientId;
        this.serviceId = serviceId;
        this.doctorId = "6064131892cd230c287d5bd4";
        this.date = date;
        this.time = time;
        this.note = note;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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
}
