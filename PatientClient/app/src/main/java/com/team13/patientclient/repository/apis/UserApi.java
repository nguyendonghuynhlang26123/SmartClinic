package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.AccountModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<AccountModel> login(@Field("phone") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/patients/register")
    Call<AccountModel> register(@Field("phone") String phoneNumber, @Field("password") String password, @Field("patient_name") String patientName, @Field("token") String token);
}
