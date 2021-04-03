package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.ServicePackApi;

public class ServicePackService {
    ServicePackApi api = RetrofitSingleton.getInstance().create(ServicePackApi.class);

    public void get(OnResponse<ServicePack[]> callback) {api.getAll().enqueue(callback);}

    public void get(int limit, OnResponse<ServicePack[]> callback) {api.getAll(limit).enqueue(callback);}
}
