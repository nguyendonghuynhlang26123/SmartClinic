package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

public class AccountModel {
    @SerializedName("phone")
    String phoneNumber;

    @SerializedName("user_type")
    String userType;

    @SerializedName("user_infor")
    Doctor userInfor;

    public AccountModel(String phoneNumber, Doctor userInfor) {
        this.phoneNumber = phoneNumber;
        this.userInfor = userInfor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Doctor getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(Doctor userInfor) {
        this.userInfor = userInfor;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
