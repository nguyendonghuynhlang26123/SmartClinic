package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.Doctor;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.DoctorApi;

import retrofit2.Callback;
import retrofit2.Retrofit;

public class DoctorService {
    DoctorApi api = RetrofitSingleton.getInstance().create(DoctorApi.class);

    public void getDoctors(Callback<Doctor[]> cb) { api.get().enqueue(cb);}

    public void updateDoctor(String doctorId, Doctor doctorData, Callback<Doctor> cb){ api.updateData(doctorId, doctorData).enqueue(cb);}
}
