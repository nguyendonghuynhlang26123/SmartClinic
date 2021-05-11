package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

class Location {
    public double cor_x;
    public double cor_y;
}

public class HospitalModel {
    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Location location;

    @SerializedName("_id")
    private String id;

    @SerializedName("address")
    private String address;

    @SerializedName("opening_time")
    private String openTime;

    @SerializedName("closing_time")
    private String closeTime;

    @SerializedName("working_days")
    private String dayOfWorks;

    @SerializedName("contact_number")
    private String phone;

    @SerializedName("thumbnail")
    private String imgUrl;


    public HospitalModel(String name, Location location, String id) {
        this.name = name;
        this.location = location;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public double getCorX() {
        return location.cor_x;
    }

    public  double getCorY(){
        return  location.cor_y;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getDayOfWorks() {
        return dayOfWorks;
    }

    public void setDayOfWorks(String dayOfWorks) {
        this.dayOfWorks = dayOfWorks;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
