package com.team13.patientclient.repository.apis;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PingApi {
    @GET("/ping")
    Call<Void> ping();
}
