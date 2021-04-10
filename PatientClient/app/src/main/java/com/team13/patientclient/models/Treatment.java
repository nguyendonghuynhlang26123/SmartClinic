package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;
import com.team13.patientclient.Store;

import java.io.Serializable;
import java.util.ArrayList;

public class Treatment implements Serializable{
    @SerializedName("_id")
    String id;

    @SerializedName("appointment")
    Appointment appointment;

    @SerializedName("prescription")
    Prescription prescription;

    public Treatment(Appointment appointment, Prescription prescription) {
        this.appointment = appointment;
        this.prescription = prescription;
    }

    public Treatment(String serviceName, String date,String time, String doctorId, String doctorName, String status) {
        this.appointment = new Appointment();
        appointment.setService(new ServicePack(serviceName, "", "", 0));
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDoctor(new Doctor(doctorId, doctorName));
        appointment.setStatus(status);

        this.prescription = null;
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

    public String getStatus(){return appointment.getStatus();}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
