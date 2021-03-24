package com.team13.patientclient.repository.services;


import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.UserApi;

import retrofit2.Call;

public class AuthService {
    UserApi api = RetrofitSingleton.getInstance().create(UserApi.class);

    public Call<AccountModel> login(String phone, String password){ return api.login(phone,password);}
    public Call<AccountModel> register(String phone, String password, String patientName){ return api.register(phone,password, patientName, "Chua co FCm");}
}
