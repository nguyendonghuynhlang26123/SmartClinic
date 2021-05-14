package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.IdResponse;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.TreatmentDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TreatmentApi {
    @POST("/treatments/register")
    Call<Void> registerTreatment(@Body TreatmentDTO dto);
}
