package com.team13.patientclient.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team13.patientclient.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        Interceptor retryingInterceptor = chain -> {
            Request request = chain.request();

            // try the request
            Response response = chain.proceed(request);

            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < 3) {
                Log.d("intercept", "Request is not successful - " + tryCount);
                tryCount++;

                // retry the request
                response = chain.proceed(request);
            }

            // otherwise just pass the original response on
            return response;
        };

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieHandler cookieHandler = new CookieManager();

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(retryingInterceptor)
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
