package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.MedicineModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MedicineApi {
    @GET("/medicines?select=thumbnail&select=price&select=category&select=medicine_name")
    Call<MedicineModel[]> get();
}
