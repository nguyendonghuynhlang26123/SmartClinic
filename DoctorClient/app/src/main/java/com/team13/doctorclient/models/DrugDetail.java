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
}
