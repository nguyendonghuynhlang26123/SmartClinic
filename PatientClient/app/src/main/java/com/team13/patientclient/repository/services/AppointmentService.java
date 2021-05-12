package com.team13.patientclient.repository.services;

import com.team13.patientclient.Store;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.AppointmentApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

public class AppointmentService {
    AppointmentApi api = RetrofitSingleton.getInstance().create(AppointmentApi.class);

    public void getAppointmentByDate(String date, String doctorId, Callback<Appointment[]> callback){
        api.getByDate(date, doctorId).enqueue(callback);
    }

    public void getAppointmentById(String id, Callback<Appointment> callback){
        api.getAppointmentById(id).enqueue(callback);
    }

    public void bookAnAppointment(String patientId, String doctorId, String serviceId, String time, String date, String note, Callback<Map<String, String>> callback) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("doctor", doctorId);
        dataMap.put("patient", patientId);
        dataMap.put("service", serviceId);
        dataMap.put("time", time);
        dataMap.put("date", date);
        dataMap.put("note", note);
        api.post(dataMap).enqueue(callback);
    }
}
