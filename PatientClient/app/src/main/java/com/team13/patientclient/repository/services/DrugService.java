package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.DrugApi;

public class DrugService {
    DrugApi api = RetrofitSingleton.getInstance().create(DrugApi.class);

    public void getMinimizedData(OnResponse<DrugModel[]> callback) { api.get().enqueue(callback);}

    public void getDrugById(String id, OnResponse<DrugModel> callback) {api.getById(id).enqueue(callback);}
}
