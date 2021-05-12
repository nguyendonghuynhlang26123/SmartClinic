package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.Treatment;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.PatientApi;
import retrofit2.Callback;

public class PatientService {
    PatientApi api = RetrofitSingleton.getInstance().create(PatientApi.class);
    public void getMedicalHistory(String patientId, Callback<Treatment[]> cb) { api.getMedicalHistory(patientId).enqueue(cb);}
}
