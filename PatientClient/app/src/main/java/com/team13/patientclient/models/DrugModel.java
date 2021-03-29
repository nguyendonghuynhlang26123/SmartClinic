package com.team13.patientclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugModel {
    @SerializedName("medicine_name")
    String name;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("price")
    long price;

    @SerializedName("unit")
    String unit;

    @SerializedName("description")
    String description;

    @SerializedName("user_manual")
    String userManual;

    @SerializedName("ingredient")
    String ingredients;

    @SerializedName("volume")
    String volume;

    @SerializedName("brand")
    String brand;

    @SerializedName("from")
    String fromCountry;

    @SerializedName("preservation")
    String preservation;

    @SerializedName("category")
    String[] category;

    @SerializedName("id")
    @Expose(serialize = false)
    String id;

    public DrugModel(String name, String thumbnail, long price, String unit, String description, String userManual, String ingredients, String volume, String brand, String fromCountry, String preservation, String[] category) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.userManual = userManual;
        this.ingredients = ingredients;
        this.volume = volume;
        this.brand = brand;
        this.fromCountry = fromCountry;
        this.preservation = preservation;
        this.category = category;
    }

    public DrugModel() {
        this.name = "";
        this.thumbnail = "";
        this.price = 0;
        this.unit = "";
        this.description = "";
        this.userManual = "";
        this.ingredients = "";
        this.volume = "";
        this.brand = "";
        this.fromCountry = "";
        this.preservation = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserManual() {
        return userManual;
    }

    public void setUserManual(String userManual) {
        this.userManual = userManual;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
