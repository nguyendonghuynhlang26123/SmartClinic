package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.ForumModel;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.ForumApi;
import retrofit2.Callback;

public class ForumService {
    ForumApi api = RetrofitSingleton.getInstance().create(ForumApi.class);

    public void get(int page, Callback<ForumModel> cb) { api.get(page).enqueue(cb);}

    public void search(int page, String search, Callback<ForumModel> cb) {api.get(page, search).enqueue(cb);}

    public void createTopic(ForumModel.Topic topic, Callback<ForumModel.Topic> cb) { api.create(topic).enqueue(cb);}

    public void publishAnswer(ForumModel.Answer answer , Callback<ForumModel.Answer> cb) { api.publishAnswer(answer).enqueue(cb);}
}
