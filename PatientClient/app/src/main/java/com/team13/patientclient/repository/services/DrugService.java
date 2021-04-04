package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.DrugApi;

import retrofit2.Callback;

public class DrugService {
    DrugApi api = RetrofitSingleton.getInstance().create(DrugApi.class);

    public void getMinimizedData(Callback<DrugModel[]> callback) { api.get().enqueue(callback);}

    public void getMinimizedData(String categoryId, Callback<DrugModel[]> callback) { api.getByCategory(categoryId).enqueue(callback);}

    public void getDrugById(String id, Callback<DrugModel> callback) {api.getById(id).enqueue(callback);}
}
