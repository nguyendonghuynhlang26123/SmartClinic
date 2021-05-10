package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Doctor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorApi {
    @GET("/doctors/{id}")
    Call<Doctor> getDoctorById(@Path("id") String id);
}
