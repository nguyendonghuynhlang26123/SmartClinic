package com.team13.doctorclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugModel implements Serializable {
    @SerializedName("medicine_name")
    String name;

    @SerializedName("price")
    long price;

    @SerializedName("_id")
    @Expose(serialize = false)
    String id;

    public DrugModel(String name, long price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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