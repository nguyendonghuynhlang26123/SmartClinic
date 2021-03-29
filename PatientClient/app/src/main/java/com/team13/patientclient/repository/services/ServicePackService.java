package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.MedicalServiceApi;

public class MedicalServicesService {
    MedicalServiceApi api = RetrofitSingleton.getInstance().create(MedicalServiceApi.class);

    void get(OnResponse<ServicePack[]> callback) {api.getAll().enqueue(callback);}

    void get(int limit, OnResponse<ServicePack[]> callback) {api.getAll(limit).enqueue(callback);}
}
