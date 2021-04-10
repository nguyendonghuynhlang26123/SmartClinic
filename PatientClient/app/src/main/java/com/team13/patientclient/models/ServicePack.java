package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePack implements Serializable {
    @SerializedName("service_name")
    String name;

    @SerializedName("service_description")
    String description="";

    @SerializedName("service_thumbnail")
    String serviceIcon;

    @SerializedName("service_price")
    long price;

    @SerializedName("_id")
    String id;

    public ServicePack(String name, String description, String serviceIcon, long price) {
        this.name = name;
        this.description = description;
        this.serviceIcon = serviceIcon;
        this.price = price;
    }

    public ServicePack() {
        this.name = "";
        this.description = "";
        this.serviceIcon = "";
        this.price = 0;
    }
    public ServicePack(String id) {
        this.name = "";
        this.description = "";
        this.serviceIcon = "";
        this.price = 0;
        this.id=id;
    }

    public boolean isEmpty(){
        return this.name.isEmpty() && this.description.isEmpty() && this.serviceIcon.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
