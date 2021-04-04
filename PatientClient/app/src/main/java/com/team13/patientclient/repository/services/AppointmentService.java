package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.AppointmentApi;

import retrofit2.Callback;

public class AppointmentService {
    AppointmentApi api = RetrofitSingleton.getInstance().create(AppointmentApi.class);

    public void bookAnAppointment(Appointment data, Callback<Appointment> callback) {
        api.post(data).enqueue(callback);
    }
}
