package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.DrugModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DrugApi {
    @GET("/medicines/{id}")
    Call<DrugModel> getById(@Path("id") String id);

    @GET("/medicines?select[0]=thumbnail&select[1]=medicine_name")
    Call<DrugModel[]> searchMedicineByName(@Query("search") String searchKey, @Query("limit") int limit);
}
