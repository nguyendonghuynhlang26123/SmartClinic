package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.ServicePack;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MedicalServiceApi {
    @GET("/medical-services")
    Call<ServicePack[]> getAll();

    @GET("/medical-services")
    Call<ServicePack[]> getAll(@Query("limit") int limit);
}
