package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.HospitalModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HospitalApi {
    @GET("/hospitals/{id}")
    Call<HospitalModel> getOne(@Path("id") String id);
}
