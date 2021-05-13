package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Prescription implements Serializable{
    @SerializedName("_id")
    String id;

    @SerializedName("medicine_list")
    ArrayList<DrugDetail> drugList;

    @SerializedName("note")
    String note;

    @SerializedName("symptoms")
    String symptom;

    @SerializedName("diagnosis")
    String diagnose;


    public Prescription(ArrayList<DrugDetail> drugList, String note, String symptom, String diagnose) {
        this.drugList = drugList;
        this.note = note;
        this.symptom = symptom;
        this.diagnose = diagnose;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
