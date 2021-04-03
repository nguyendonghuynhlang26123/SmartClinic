package com.team13.patientclient.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team13.patientclient.MyApp;
import com.team13.patientclient.NotificationHandler;
import com.team13.patientclient.models.ErrorResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class OnResponse<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()){
            Runnable runnable = () -> onRequestSuccess(response.body());
            runnable.run();
        }
        else{
            ErrorResponse errorResponse = null;
            try {
                errorResponse = new Gson().fromJson(
                        response.errorBody().string(),
                        ErrorResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("LONG_DEBUG", new Gson().toJson(errorResponse));
            //if (errorResponse != null) NotificationHandler.sendNotification(MyApp.getContext(), "Error", errorResponse.getMessage());
            //if (ctx != null) Toast.makeText(MyApp.getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
            NotificationHandler.sendNotification(MyApp.getContext(), "Error", errorResponse.getMessage());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("LONG_FAILURE", t.getMessage());
    }

    public abstract void onRequestSuccess(T response);

}