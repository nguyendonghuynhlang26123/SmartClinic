package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.Doctor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorApi {
    @GET("/doctors?select=name")
    Call<Doctor[]> get();

    @PUT("/doctors/{id}")
    Call<Doctor> updateData(@Path("id") String id, @Body Doctor data);
}
