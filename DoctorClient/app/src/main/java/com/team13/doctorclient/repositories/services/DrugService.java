package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.DrugModel;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.DrugApi;

import retrofit2.Callback;

public class DrugService {
    DrugApi api = RetrofitSingleton.getInstance().create(DrugApi.class);

    public void getDrugById(String id, Callback<DrugModel> callback) {api.getById(id).enqueue(callback);}

    public void searchDrug(String drugKey, int limit, Callback<DrugModel[]> callBack){api.searchMedicineByName(drugKey, limit).enqueue(callBack);}
}
