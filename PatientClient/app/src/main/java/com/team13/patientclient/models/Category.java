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

    @Expose(deserialize = false, serialize = false)
    ArrayList<DrugModel> drugList = new ArrayList<>();

    public Category(String name, DrugModel[] drugList){
        this.name = name;
        this.drugList = new ArrayList<>(Arrays.asList(drugList));
    }
    public Category(String name){
        this.name = name;
        this.drugList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.drugList.add(new DrugModel());
        }
    }
    public void setDrugList(DrugModel[] drugList){
        this.drugList = new ArrayList<>(Arrays.asList(drugList));
    }
    public ArrayList<DrugModel> getDrugList(){
        return drugList;
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
