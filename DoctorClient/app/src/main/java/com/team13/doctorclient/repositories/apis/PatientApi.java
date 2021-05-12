package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.Treatment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientApi {
    @GET("/treatments?populate=patient+service+prescription+prescription.medicine_list.medicine")
    Call<Treatment[]> getMedicalHistory(@Query("patient_id") String patientId);
}
