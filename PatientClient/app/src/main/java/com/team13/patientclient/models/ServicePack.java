package com.team13.patientclient.models;

public class ServicePack {
    public String name;
    public String description="";
    public int serviceIcon;
    public int price;
    public ServicePack(String name, int serviceIcon, int price){
        this.name = name;
        this.serviceIcon = serviceIcon;
        this.price = price;
    }
}
