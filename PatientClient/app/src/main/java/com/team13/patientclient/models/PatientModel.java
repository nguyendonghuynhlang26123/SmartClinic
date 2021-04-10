package com.team13.patientclient.models;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team13.patientclient.Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    long dateOfBirth;

    @SerializedName("patient_weight")
    double weight;

    @SerializedName("token")
    String token;

    @SerializedName("medical_history")
    String[] history;

    @SerializedName("current_appointment")
    String currentAppointment;


    public PatientModel(String name, String avatarUrl, String gender, long dateOfBirth, double weight) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.token = "No FCM yet";
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthString(){
        if (dateOfBirth == 0) return "";
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(Utils.DATE_PATTERN);
        Date date = new Date(dateOfBirth);
        return dateFormat.format(date);
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(String formatedDOB) {
        this.dateOfBirth = Utils.dateStringToNumber(formatedDOB);
    }

    public int getAge() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(Utils.DATE_PATTERN);
        Date date = new Date(dateOfBirth);
        return Utils.calculateAge(date, new Date());
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

    public String[] getHistory() {
        return history;
    }

    public void setHistory(String[] history) {
        this.history = history;
    }

    public String getCurrentAppointment() {
        return currentAppointment;
    }

    public void setCurrentAppointment(String currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
