package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.DrugModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DrugApi {
    @GET("/medicines?select=thumbnail&select=price&select=category&select=medicine_name")
    Call<DrugModel[]> get();

    @GET("/medicines/{id}")
    Call<DrugModel> getById(@Path("id") String id);
}
