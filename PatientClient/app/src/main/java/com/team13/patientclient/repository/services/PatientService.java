package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.PatientModel;
import com.team13.patientclient.models.Treatment;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.PatientApi;

import retrofit2.Callback;

public class PatientService {
    PatientApi api = RetrofitSingleton.getInstance().create(PatientApi.class);

    public void updatePatientProfile(String id, PatientModel data, Callback<Void> cb) { api.update(id, data).enqueue(cb);}

    public void removeCurrentAppointment(String patientId, String appointmentId, Callback<Void> cb) {api.cancelAppointment(patientId,appointmentId).enqueue(cb);}

    public void getMedicalHistory(String patientId, Callback<Treatment[]> cb) { api.getMedicalHistory(patientId).enqueue(cb);}
}
