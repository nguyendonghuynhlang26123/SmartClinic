package com.team13.patientclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientModel {
    @SerializedName("_id")
    @Expose(serialize =  false)
    String id;

    @SerializedName("patient_name")
    String name;

    @SerializedName("patient_avatar")
    String avatarUrl;

    @SerializedName("patient_gender")
    String gender;

    @SerializedName("patient_dob")
    String dateOfBirth;

    @SerializedName("patient_weight")
    double weight;

    @SerializedName("token")
    String token;

    public PatientModel(String name, String avatarUrl, String gender, String dateOfBirth, double weight, String token) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
