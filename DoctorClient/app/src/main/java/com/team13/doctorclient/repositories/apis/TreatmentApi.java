package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.Prescription;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TreatmentApi {
    @POST("/treatments")
    @FormUrlEncoded
    Call<Void> createTreatment(@FieldMap Map<String, String> data);

    @POST("/prescriptions")
    Call<Prescription> createPrescription(@Body Prescription data);
}
