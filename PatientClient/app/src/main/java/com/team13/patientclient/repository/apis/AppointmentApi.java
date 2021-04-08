package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Appointment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppointmentApi {
    @GET("/appointments?select=time")
    Call<Appointment[]> getByDate(@Query("date") String date, @Query("service_id") String serviceID);

    @POST("/appointments")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Appointment> post(@Body Appointment model);
}
