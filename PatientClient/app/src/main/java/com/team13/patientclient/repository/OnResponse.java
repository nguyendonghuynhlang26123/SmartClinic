package com.team13.patientclient.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team13.patientclient.activities.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public abstract class OnResponse<T> implements Callback<T> {
    Context ctx = null;
    public OnResponse(Context ctx) {
        this.ctx = ctx;
    }

    public OnResponse() {
    }

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
            if (ctx != null) Toast.makeText(ctx, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("LONG_FAILURE", t.getMessage());
    }

    public abstract void onRequestSuccess(T response);

}