package com.team13.patientclient;

public class Appointment {
    String patientId;
    String doctorId;
    String date;
    String time;
    public Appointment(String patientId, String doctorId, String date, String time){
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }
}
