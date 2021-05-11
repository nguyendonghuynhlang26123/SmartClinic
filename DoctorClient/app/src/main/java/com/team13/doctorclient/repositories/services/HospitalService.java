package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.HospitalApi;

import retrofit2.Callback;

public class HospitalService {
    HospitalApi api = RetrofitSingleton.getInstance().create(HospitalApi.class);

    public void getHospital(String id, Callback<HospitalModel> callback) { api.getOne(id).enqueue(callback);}
}
