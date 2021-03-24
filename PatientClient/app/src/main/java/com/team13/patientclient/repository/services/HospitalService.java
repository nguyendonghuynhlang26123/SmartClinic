package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.HospitalModel;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.HospitalApi;

import retrofit2.Call;

public class HospitalService {
    HospitalApi api = RetrofitSingleton.getInstance().create(HospitalApi.class);

    public Call<HospitalModel> getHospital(String id) {return api.getOne(id);}
}
