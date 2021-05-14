package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Appointment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppointmentApi {
    @GET("/appointments/{id}")
    Call<Appointment> getAppointmentById(@Path("id") String appointmentId);

    @GET("/appointments?select=time&status[0]=PENDING&status[1]=PROCESSING")
    Call<Appointment[]> getByDate(@Query("date") String date, @Query("doctor_id") String doctorId);

    @POST("/appointments")
    @FormUrlEncoded
    Call<Map<String, String>> post(@FieldMap Map<String, String> params);

    @DELETE("/appointments/{id}")
    Call<Void> deleteAppointment(@Path("id") String appointmentId);

    @PUT("/appointments/{id}")
    @FormUrlEncoded
    Call<Void> updateAppointment(@Path("id") String appointmentId, @FieldMap Map<String, String> data);
}
