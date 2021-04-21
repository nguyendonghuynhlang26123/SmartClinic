package com.team13.doctorclient.models;

import java.io.Serializable;

public class ServicePack implements Serializable {
    String name;
    String description;
    long price;
    String id;

    public ServicePack(String name, String description, long price, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
