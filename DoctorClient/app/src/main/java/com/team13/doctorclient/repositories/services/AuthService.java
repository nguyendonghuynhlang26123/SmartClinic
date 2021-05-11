package com.team13.doctorclient.repositories.services;


import com.team13.doctorclient.models.AccountModel;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.UserApi;

import retrofit2.Callback;

public class AuthService {
    UserApi api = RetrofitSingleton.getInstance().create(UserApi.class);

    public void login(String phone, String password, Callback<AccountModel> callback){ api.login(phone,password).enqueue(callback);}
}
