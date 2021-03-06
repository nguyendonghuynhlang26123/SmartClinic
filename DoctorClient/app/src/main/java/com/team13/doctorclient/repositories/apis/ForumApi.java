package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.ForumModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ForumApi {
    @GET("/forums?per_page=10")
    Call<ForumModel> get(@Query("page") int page);

    @GET("/forums?per_page=10")
    Call<ForumModel> get(@Query("page") int page, @Query("search") String searchKey);

    @POST("/forums")
    Call<ForumModel.Topic> create(@Body ForumModel.Topic topic);

    @PUT("/forums/answer/{id}")
    Call<ForumModel.Answer> publishAnswer(@Path("id") String forumId, @Body ForumModel.Answer answer);
}
