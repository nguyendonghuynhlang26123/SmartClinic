package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Hospital;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HospitalApi {
    @GET("/hospitals/{id}")
    Call<Hospital> getOne(@Path("id") String id);
}
