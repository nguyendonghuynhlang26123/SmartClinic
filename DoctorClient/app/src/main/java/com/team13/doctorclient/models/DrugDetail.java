package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugDetail implements Serializable {
    @SerializedName("medicine")
    DrugModel drugModel;

    @SerializedName("quantity")
    int quantity;

    @SerializedName("note")
    String note;

    public DrugDetail(DrugModel drugModel, int quantity, String note) {
        this.drugModel = drugModel;
        this.quantity = quantity;
        this.note = note;
    }

    public DrugDetail() {
    }

    public TreatmentDTO.DrugDetailDTO toDTO(){
        return new TreatmentDTO.DrugDetailDTO(drugModel.getId(), quantity, note);
    }

    public DrugModel getDrugModel() {
        return drugModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNote() {
        return note;
    }

    public String getName() {return drugModel.getName();}

    public void setDrugModel(DrugModel drugModel) {
        this.drugModel = drugModel;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
