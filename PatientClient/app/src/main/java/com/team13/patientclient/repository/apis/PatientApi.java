package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.MedicalHistory;
import com.team13.patientclient.models.PatientModel;
import com.team13.patientclient.models.Treatment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatientApi {
    @PUT("/patients/{id}")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Void> update(@Path("id") String id, @Body PatientModel data);


    @POST("/patients/cancel/{id}")
    @FormUrlEncoded
    Call<Void> cancelAppointment(@Path("id") String id, @Field("appointment_id") String appointmentId);

    @GET("/patients/medical_history/{id}")
    Call<Treatment[]> getMedicalHistory(@Path("id") String id);
}
