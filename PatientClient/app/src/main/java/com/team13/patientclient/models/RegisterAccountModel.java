package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

public class RegisterAccountModel {
    @SerializedName("patient_name")
    String name;

    @SerializedName("phone")
    String phoneNumber;

    @SerializedName("password")
    String password;

    public RegisterAccountModel(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
