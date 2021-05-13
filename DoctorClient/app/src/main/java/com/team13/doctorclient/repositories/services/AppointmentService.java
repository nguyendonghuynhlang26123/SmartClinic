package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.AppointmentApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.http.Path;

public class AppointmentService {
    AppointmentApi api = RetrofitSingleton.getInstance().create(AppointmentApi.class);

    public void getAppointmentByDate(String date, Callback<Appointment[]> callback){
        api.getByDate(date).enqueue(callback);
    }

    public void getAppointmentByDate(String doctorId, String date, String[] statuses, Callback<Appointment[]> callback){
        api.getByDate(doctorId,date, statuses).enqueue(callback);
    }

    public void getAppointmentById(String id, Callback<Appointment> callback){
        api.getAppointmentById(id).enqueue(callback);
    }

    public void bookAnAppointment(String doctorId, Appointment data, String patientId, Callback<Map<String, String>> callback) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("doctor", doctorId);
        dataMap.put("patient", patientId);
        dataMap.put("service", data.getService().getId());
        dataMap.put("time", data.getTime());
        dataMap.put("date", data.getDate());
        dataMap.put("note", data.getNote());
        api.post(dataMap).enqueue(callback);
    }

    public void updateAnAppointment(String id, String status, Callback<Void> cb) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        api.put(id, params).enqueue(cb);
    }
}
