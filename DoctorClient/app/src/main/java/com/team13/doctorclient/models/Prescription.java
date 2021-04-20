package com.team13.doctorclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Prescription{
    String Id;
    String patientName;
    ArrayList<Drug> drugList;
    String note;
    String symptom;
    String diagnose;
    String dateStart, dateEnd;


    public Prescription(String Id,String patientName,ArrayList<Drug> drugList, String note, String symptom, String diagnose,String dateStart,String dateEnd) {
        this.Id=Id;
        this.patientName=patientName;
        this.drugList = drugList;
        this.note = note;
        this.symptom = symptom;
        this.diagnose = diagnose;
        this.dateStart= dateStart;
        this.dateEnd=dateEnd;
    }

    public ArrayList<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(ArrayList<Drug> drugList) {
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
    public void setIdPrescription(String Id){ this.Id = Id;}
    public String getIdPrescription(){return Id;}
    public void setPatientName(String patientName){this.patientName=patientName;}
    public String getPatientName(){return patientName;}

    public String getDateStart() { return dateStart; }

    public String getDateEnd() { return dateEnd; }
}
