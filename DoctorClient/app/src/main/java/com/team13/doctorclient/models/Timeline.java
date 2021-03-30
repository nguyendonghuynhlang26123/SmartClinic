package com.team13.doctorclient.models;

public class Timeline {
    public boolean isSeized;
    String patientName;
    String treatment;
    String time;
    public Timeline(boolean isSeized,String patientName,String treatment,String time){
        this.isSeized=isSeized;
        this.patientName=patientName;
        this.treatment=treatment;
        this.time=time;
    }
}
