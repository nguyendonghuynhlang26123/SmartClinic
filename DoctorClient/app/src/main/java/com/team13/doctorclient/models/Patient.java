package com.team13.doctorclient.models;

public class Patient {
    String patientId;
    String patientName;
    String patientDiagnostic;
    String patientTreatment;

    public Patient(String patientId, String patientName, String patientDiagnostic, String patientTreatment) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientDiagnostic = patientDiagnostic;
        this.patientTreatment = patientTreatment;
    }
}
