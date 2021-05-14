package com.team13.doctorclient.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugModel implements Serializable {
    @SerializedName("medicine_name")
    String name;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("price")
    long price;

    @SerializedName("_id")
    String id;

    public DrugModel(String name, long price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
