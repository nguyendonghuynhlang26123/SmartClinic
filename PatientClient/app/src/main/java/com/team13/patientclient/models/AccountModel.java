package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

public class AccountModel {
    @SerializedName("phone")
    String phoneNumber;

    @SerializedName("user_infor")
    PatientModel userInfor;

    public AccountModel(String phoneNumber, PatientModel userInfor) {
        this.phoneNumber = phoneNumber;
        this.userInfor = userInfor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PatientModel getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(PatientModel userInfor) {
        this.userInfor = userInfor;
    }
}
