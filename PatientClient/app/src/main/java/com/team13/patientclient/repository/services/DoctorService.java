package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.Doctor;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.DoctorApi;

import retrofit2.Callback;

public class DoctorService {
    DoctorApi api = RetrofitSingleton.getInstance().create(DoctorApi.class);

    public void getDoctorById(String doctorId, Callback<Doctor> cb) { api.getDoctorById(doctorId).enqueue(cb);}
}
