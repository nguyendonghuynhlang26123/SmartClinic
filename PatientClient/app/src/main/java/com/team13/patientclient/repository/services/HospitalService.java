package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.Hospital;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.HospitalApi;

import retrofit2.Call;

public class HospitalService {
    HospitalApi api = RetrofitSingleton.getInstance().create(HospitalApi.class);

    public Call<Hospital> getHospital(String id) {return api.getOne(id);}
}
