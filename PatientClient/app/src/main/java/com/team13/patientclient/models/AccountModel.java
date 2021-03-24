package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("phone")
    String phoneNumber;

    @SerializedName("password")
    String password;

    @SerializedName("user_infor")
    Patient userInfor;

    public Account(String phoneNumber, String password, Patient userInfor) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userInfor = userInfor;
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

    public Patient getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(Patient userInfor) {
        this.userInfor = userInfor;
    }
}
