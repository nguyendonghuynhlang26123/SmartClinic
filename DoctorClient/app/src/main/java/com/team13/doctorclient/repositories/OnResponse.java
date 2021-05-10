package com.team13.doctorclient.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.team13.doctorclient.MyApp;
import com.team13.doctorclient.NotificationHandler;
import com.team13.doctorclient.models.ErrorResponse;

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
            NotificationHandler.sendNotification(MyApp.getContext(), "Error", errorResponse.getMessage());

            ErrorResponse finalErrorResponse = errorResponse;
            Runnable runnable = () -> onRequestFailed(finalErrorResponse);
            runnable.run();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("LONG_FAILURE", t.getMessage());
    }

    public abstract void onRequestSuccess(T response);
    public abstract void onRequestFailed(ErrorResponse response);
}
