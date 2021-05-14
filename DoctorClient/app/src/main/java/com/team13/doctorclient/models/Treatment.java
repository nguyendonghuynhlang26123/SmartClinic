package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Treatment implements Serializable {
    @SerializedName("_id")
    String id;

    @SerializedName("patient")
    PatientModel patient;

//    @SerializedName("doctor")
//    Doctor doctor;

    @SerializedName("service")
    ServicePack servicePack;

    @SerializedName("time")
    String time;

    @SerializedName("date")
    String date;

    @SerializedName("note")
    String note;

    @SerializedName("prescription")
    Prescription prescription;

    public Treatment(Appointment appointment, Prescription prescription){
        this.prescription = prescription;
        note = appointment.getNote();
        date = appointment.getDate();
        time = appointment.getTime();
        servicePack = appointment.getService();
        patient = appointment.getPatient();
        id = null;
    }

    public TreatmentDTO getDTO(String doctorId, String appointmentId){
       ArrayList<TreatmentDTO.DrugDetailDTO> detailDTO = new ArrayList<>();
        for (DrugDetail drugDetail :
                prescription.getDrugList()) {
            detailDTO.add(drugDetail.toDTO());
        }
        return new TreatmentDTO(doctorId,appointmentId,prescription.symptom, prescription.diagnose, prescription.note, detailDTO.toArray(new TreatmentDTO.DrugDetailDTO[0]));
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public String getServicePack() {
        return servicePack.getName();
    }
    public String getServicePackId() {
        return servicePack.getId();
    }

    public String getDate() {
        return date;
    }
//
//    public String getDoctorName() {
//        return doctor.getDoctorName();
//    }
//    public String getDoctorId() {
//        return doctor.getId();
//    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

//    public Doctor getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(Doctor doctor) {
//        this.doctor = doctor;
//    }

    public void setServicePack(ServicePack servicePack) {
        this.servicePack = servicePack;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

