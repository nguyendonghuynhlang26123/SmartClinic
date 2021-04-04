package com.team13.patientclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Treatment implements Serializable{
    String servicePack;
    String date;
    String treatmentId;
    String doctorId;
    String doctorName;
    String patientId;
    String patientName;
    String time;
    Prescription prescription;

    public Treatment(String treatmentId, String servicePack, String date,String time, String doctorId, String doctorName, String patientId, String patientName) {
        this.treatmentId = treatmentId;
        this.servicePack = servicePack;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.patientName = patientName;
    }
    public class Prescription implements Serializable {
        ArrayList<DrugDetail> drugList;
        String note;
        String patientName;
        String symptom;
        String diagnose;
        String doctorName;
        String date;

        public Prescription(ArrayList<DrugDetail> drugList, String note, String patientName, String symptom, String diagnose, String doctorName, String date) {
            this.drugList = drugList;
            this.note = note;
            this.patientName = patientName;
            this.symptom = symptom;
            this.diagnose = diagnose;
            this.doctorName = doctorName;
            this.date = date;
        }
        public ArrayList<DrugDetail> getDrugList() {
            return drugList;
        }

        public String getPatientName() {
            return patientName;
        }

        public String getDate() {
            return date;
        }

        public String getSymptom() {
            return symptom;
        }

        public String getDiagnose() {
            return diagnose;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public String getNote() {
            return note;
        }
    }
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServicePack() {
        return servicePack;
    }

    public String getDate() {
        return date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getTime() {
        return time;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public void createPrescription(ArrayList<DrugDetail> drugList, String note,String symptom, String diagnose){
        prescription = new Prescription(drugList, note,patientName,symptom,diagnose,doctorName,time+", "+date);
    }
}
