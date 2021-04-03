package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.DrugModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DrugApi {
    @GET("/medicines?select=thumbnail&select=medicine_name")
    Call<DrugModel[]> get();

    @GET("/medicines/{id}")
    Call<DrugModel> getById(@Path("id") String id);

    @GET("/medicines?select=thumbnail&select=medicine_name")
    Call<DrugModel[]> getByCategory(@Query("category") String categoryId);
}
