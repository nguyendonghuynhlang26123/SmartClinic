package com.team13.doctorclient.repositories.apis;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PingApi {
    @GET("/ping")
    Call<Void> ping();
}
