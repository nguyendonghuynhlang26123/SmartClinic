package com.team13.doctorclient.models;

public class PatientTimeline {
    String patientId;
    String patientName;
    String prescriptionId;
    String patientSymptom;
    String patientDiagnostic;
    String patientTreatment;
    String date;
    String time;
    public PatientTimeline(String patientId, String patientName, String prescriptionId, String patientSymptom, String patientDiagnostic, String patientTreatment, String date, String time) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.prescriptionId = prescriptionId;
        this.patientSymptom = patientSymptom;
        this.patientDiagnostic = patientDiagnostic;
        this.patientTreatment = patientTreatment;
        this.date = date;
        this.time = time;
    }


    public java.lang.String getPrescriptionId() {
        return prescriptionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPatientSymptom() {
        return patientSymptom;
    }

    public String getPatientDiagnostic() {
        return patientDiagnostic;
    }

    public String getPatientTreatment() {
        return patientTreatment;
    }
}

