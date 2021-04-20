package com.team13.doctorclient.models;

public class Drug {
    String drugId;
    String drugName;
    String drugQuality;
    String drugNote;

    public Drug(String drugId, String drugName, String drugQuality, String drugNote) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugQuality = drugQuality;
        this.drugNote = drugNote;
    }

    public String getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugNote() {
        return drugNote;
    }

    public String getDrugQuality() {
        return drugQuality;
    }
}
