package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Doctor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorApi {
    @GET("/doctors/{id}")
    Call<Doctor> getDoctorById(@Path("id") String id);

    @GET("/doctors")
    Call<Doctor[]> getDoctorByService(@Query("service") String serviceIds);
}
