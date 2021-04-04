package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.ServicePackApi;

import retrofit2.Callback;

public class ServicePackService {
    ServicePackApi api = RetrofitSingleton.getInstance().create(ServicePackApi.class);

    public void get(Callback<ServicePack[]> callback) {api.getAll().enqueue(callback);}

    public void get(int limit, Callback<ServicePack[]> callback) {api.getAll(limit).enqueue(callback);}
}
