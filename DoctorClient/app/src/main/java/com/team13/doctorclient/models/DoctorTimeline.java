package com.team13.doctorclient.models;

import java.io.Serializable;

public class DoctorTimeline implements Serializable {
    public boolean isSeized;
    String patientName;
    String treatment;
    String time;
    String symptom;
    public DoctorTimeline(boolean isSeized, String patientName, String treatment, String day, String time, String symptom){
        this.isSeized=isSeized;
        this.patientName=patientName;
        this.treatment=treatment;
        this.time=day+" "+ time;
        this.symptom=symptom;
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

    public String getSymptom(){return  symptom;}
}
