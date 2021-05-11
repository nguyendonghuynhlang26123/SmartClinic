package com.team13.doctorclient.models;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientModel {
    @SerializedName("_id")
    @Expose(serialize =  false)
    String id;

    @SerializedName("patient_name")
    String name;

    @SerializedName("medical_history")
    String[] history;

    public PatientModel(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getHistory() {
        return history;
    }

    public void setHistory(String[] history) {
        this.history = history;
    }
}
