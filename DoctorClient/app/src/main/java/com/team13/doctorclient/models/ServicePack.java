package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePack implements Serializable {
    @SerializedName("service_name")
    String name;

    @SerializedName("service_description")
    String description="";

    @SerializedName("service_price")
    long price;

    @SerializedName("_id")
    String id;

    public ServicePack(String name, String description, long price, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ServicePack() {
        this.name = "";
        this.description = "";
        this.price = 0;
    }
    public ServicePack(String id) {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.id=id;
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
