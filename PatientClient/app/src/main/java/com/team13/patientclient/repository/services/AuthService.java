package com.team13.patientclient.repository.services;


import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.UserApi;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthService {
    UserApi api = RetrofitSingleton.getInstance().create(UserApi.class);

    public void login(String phone, String password, Callback<AccountModel> callback){ api.login(phone,password).enqueue(callback);}
    public void register(String phone, String password, String patientName, OnResponse<AccountModel> callback){
        api.register(phone,password, patientName, "Chua co FCm").enqueue(callback);
    }
}
