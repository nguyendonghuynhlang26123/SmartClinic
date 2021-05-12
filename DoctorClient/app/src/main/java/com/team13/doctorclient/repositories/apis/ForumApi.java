package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.ForumModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ForumApi {
    @GET("/forums?per_page=5")
    Call<ForumModel> get(@Query("page") int page);

    @GET("/forums?per_page=5")
    Call<ForumModel> get(@Query("page") int page, @Query("search") String searchKey);

    @POST("/forums")
    Call<ForumModel.Topic> create(@Body ForumModel.Topic topic);

    @PUT("/forums/answers/{id}")
    @FormUrlEncoded
    Call<ForumModel.Answer> publishAnswer(@Body ForumModel.Answer answer);
}
