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

    public void getAppointmentByDate(String date, String serviceId, Callback<Appointment[]> callback){
        api.getByDate(date, serviceId).enqueue(callback);
    }

    public void getAppointmentById(String id, Callback<Appointment> callback){
        api.getAppointmentById(id).enqueue(callback);
    }

    public void bookAnAppointment(Appointment data, String patientId, Callback<Map<String, String>> callback) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("doctor", data.getDoctor().getId());
        dataMap.put("patient", patientId);
        dataMap.put("service", data.getServiceId());
        dataMap.put("time", data.getTime());
        dataMap.put("date", data.getDate());
        dataMap.put("note", data.getNote());
        api.post(dataMap).enqueue(callback);
    }
}
