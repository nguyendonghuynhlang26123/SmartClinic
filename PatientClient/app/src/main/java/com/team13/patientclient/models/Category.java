package com.team13.patientclient.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Category {
    String name;
    ArrayList<DrugModel> drugList;
    public Category(String name, DrugModel[] drugList){
        this.name = name;
        this.drugList = new ArrayList<>(Arrays.asList(drugList));
    }
    public Category(String name){
        this.name = name;
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
}
