package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.PatientModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.PatientApi;

public class PatientService {
    PatientApi api = RetrofitSingleton.getInstance().create(PatientApi.class);

    public void updatePatientProfile(String id, PatientModel data, OnResponse<Void> cb) { api.update(id, data).enqueue(cb);}
}