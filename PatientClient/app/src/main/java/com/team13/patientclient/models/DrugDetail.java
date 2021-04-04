package com.team13.patientclient.models;

import java.io.Serializable;

public class DrugDetail implements Serializable {
    DrugModel drugModel;
    int quantity;
    String note;
    long total=0;
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
    public long getTotal(){
        total = quantity * drugModel.price;
        return total;
    }
}
