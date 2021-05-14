package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.Doctor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorApi {
    @GET("/doctors?select=name")
    Call<Doctor[]> get();
}
