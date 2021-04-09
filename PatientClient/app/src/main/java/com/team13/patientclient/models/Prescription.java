package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Prescription {
    @SerializedName("medicine_list")
    ArrayList<DrugDetail> drugList;

    @SerializedName("note")
    String note;

    @SerializedName("symptoms")
    String symptom;

    @SerializedName("diagnosis")
    String diagnose;

    @SerializedName("doctor")
    Doctor doctor;

    @SerializedName("date")
    String date;


    public ArrayList<DrugDetail> getDrugList() {
        return drugList;
    }

    public void setDrugList(ArrayList<DrugDetail> drugList) {
        this.drugList = drugList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
