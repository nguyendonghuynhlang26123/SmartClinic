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
}
