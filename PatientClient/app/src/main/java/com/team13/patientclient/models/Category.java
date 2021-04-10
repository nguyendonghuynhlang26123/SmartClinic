package com.team13.patientclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class Category {
    @SerializedName("category_name")
    String name;

    @SerializedName("_id")
    String id;

    public Category(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
