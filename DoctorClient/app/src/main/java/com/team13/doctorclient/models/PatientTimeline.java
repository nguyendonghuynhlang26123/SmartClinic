package com.team13.doctorclient.models;

public class PatientTimeline {
    String patientId;
    String symptom;
    String diagnostic;
    String prescriptionId;
    public  PatientTimeline(String patientId,String symptom,String diagnostic,String prescriptionId){
        this.patientId=patientId;
        this.symptom=symptom;
        this.diagnostic=diagnostic;
        this.prescriptionId= prescriptionId;
    }
}

