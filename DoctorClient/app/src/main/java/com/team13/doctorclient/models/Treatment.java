package com.team13.doctorclient.models;

public class Treatment {
    String id;
    Appointment appointment;
    Prescription prescription;

    public Treatment(Appointment appointment, Prescription prescription) {
        this.appointment = appointment;
        this.prescription = prescription;
    }


    public Appointment getAppointment() {
        return appointment;
    }

    public Prescription getPrescription() {
        return prescription;
    }


    public String getServicePack() {
        return appointment.getService().getName();
    }

    public String getDate() {
        return appointment.getDate();
    }

    public String getTime() {
        return appointment.getTime();
    }

    public String getTreatmentId() {
        return getTreatmentId();
    }

    public String getStatus(){return appointment.getStatus();}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

