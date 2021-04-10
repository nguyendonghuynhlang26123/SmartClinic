package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

public class MedicalHistory {
    @SerializedName("appointment")
    Appointment appointment;

    @SerializedName("prescription")
    Prescription prescription;

    public MedicalHistory(Appointment appointment, Prescription prescription) {
        this.appointment = appointment;
        this.prescription = prescription;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public String getServicePack() {
        return appointment.getServiceName();
    }

    public String getDate() {
        return appointment.getDate();
    }

    public String getDoctorName() {
        return appointment.getDoctorName();
    }

    public String getTime() {
        return appointment.getTime();
    }

    public String getTreatmentId() {
        return getTreatmentId();
    }

}
