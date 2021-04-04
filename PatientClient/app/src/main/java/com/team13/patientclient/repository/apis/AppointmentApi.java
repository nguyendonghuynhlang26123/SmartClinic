package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.Appointment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppointmentApi {
    @GET("/appointments?select=time&is_expired=false")
    Call<Appointment[]> getByDate(@Query("date") String date);

    @POST("/appointments")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Appointment> post(@Body Appointment model);
}
