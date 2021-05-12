package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.Appointment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppointmentApi {
    @GET("/appointments/{id}")
    Call<Appointment> getAppointmentById(@Path("id") String appointmentId);

    @GET("/appointments?populate=patient+service+doctor&status[0]=PENDING&status[1]=PROCESSING")
    Call<Appointment[]> getByDate(@Query("date") String date);

    @GET("/appointments?populate=patient+service+doctor")
    Call<Appointment[]> getByDate(@Query("date") String date, @Query("doctor_id") String doctorId, @Query("status") String[] statuses);

    @POST("/appointments")
    @FormUrlEncoded
    Call<Map<String, String>> post(@FieldMap Map<String, String> params);

    @PUT("/appointments/{id}")
    @FormUrlEncoded
    Call<Void> put(@Path("id") String id, @FieldMap Map<String,String> params);
}
