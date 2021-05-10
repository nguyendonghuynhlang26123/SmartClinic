package com.team13.doctorclient.repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team13.doctorclient.Utils;

import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static OkHttpClient httpClient = new OkHttpClient();
    private static RetrofitSingleton instance = new RetrofitSingleton();
    private static Retrofit retrofit;

    private RetrofitSingleton() {
        retrofit = createAdapter().build();
    }

    public static Retrofit getInstance() {
        return retrofit;
    }

    private Retrofit.Builder createAdapter() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieHandler cookieHandler = new CookieManager();

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor)//.addInterceptor(retryingInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(Utils.BACK_END_API_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient);
    }
}
