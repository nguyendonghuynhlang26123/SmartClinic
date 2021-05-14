package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TreatmentDTO implements  Serializable{
    @SerializedName("appointment")
    String appointmentId;

    @SerializedName("symptoms")
    String symptoms;
    @SerializedName("diagnosis")
    String diagnosis;

    @SerializedName("prescription_note")
    String prescriptionNote;

    @SerializedName("medicine_list")
    DrugDetailDTO[] medicineList;

    @SerializedName("doctor")
    String doctor;

    public static class DrugDetailDTO implements Serializable {
        @SerializedName("medicine")
        String medicineId;

        @SerializedName("quantity")
        int quantity;

        @SerializedName("note")
        String note;

        public DrugDetailDTO(String medicineId, int quantity, String note) {
            this.medicineId = medicineId;
            this.quantity = quantity;
            this.note = note;
        }
    }

    public TreatmentDTO( String doctor, String appointmentId, String symptoms, String diagnosis, String prescriptionNote, DrugDetailDTO[] medicineList) {
        this.appointmentId = appointmentId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescriptionNote = prescriptionNote;
        this.medicineList = medicineList;
        this.doctor = doctor;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescriptionNote(String prescriptionNote) {
        this.prescriptionNote = prescriptionNote;
    }

    public void setMedicineList(DrugDetailDTO[] medicineList) {
        this.medicineList = medicineList;
    }
}
