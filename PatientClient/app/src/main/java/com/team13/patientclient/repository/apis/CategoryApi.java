package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("/categories")
    Call<Category[]> get();
}
