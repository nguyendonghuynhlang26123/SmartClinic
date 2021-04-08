package com.team13.doctorclient.models;

import java.io.Serializable;

public class DoctorTimeline implements Serializable {
    public boolean isSeized;
    String patientName;
    String treatment;
    String time;
    public DoctorTimeline(boolean isSeized, String patientName, String treatment, String day, String time){
        this.isSeized=isSeized;
        this.patientName=patientName;
        this.treatment=treatment;
        this.time=day+" "+ time;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getTime() {
        return time;
    }

    public String getTreatment() {
        return treatment;
    }
}
