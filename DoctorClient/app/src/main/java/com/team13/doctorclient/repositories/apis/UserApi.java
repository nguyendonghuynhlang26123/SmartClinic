package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.AccountModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<AccountModel> login(@Field("phone") String phoneNumber, @Field("password") String password);

}
