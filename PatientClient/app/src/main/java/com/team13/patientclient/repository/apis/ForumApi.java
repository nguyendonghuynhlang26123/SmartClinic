package com.team13.patientclient.repository.apis;

import com.team13.patientclient.models.ForumModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ForumApi {
    @GET("/forums?per_page=5")
    Call<ForumModel> get(@Query("page") int page);

    @GET("/forums?per_page=5")
    Call<ForumModel> get(@Query("page") int page, @Query("search") String searchKey);

    @POST("/forums")
    @FormUrlEncoded
    Call<ForumModel.Topics> create(@Body ForumModel.Topics topic);

    @PUT("/forums/answers/:id")
    @FormUrlEncoded
    Call<ForumModel.Answers> publishAnswer(@Body ForumModel.Answers answer);
}
