package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

class Location {
    public double cor_x;
    public double cor_y;
}

public class Hospital {
    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Location location;

    @SerializedName("_id")
    private String id;

    public Hospital(String name, Location location, String id) {
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
}
