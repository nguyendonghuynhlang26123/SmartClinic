package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.Treatment;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.TreatmentApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;


public class TreatmentService {
    TreatmentApi treatmentApi = RetrofitSingleton.getInstance().create(TreatmentApi.class);

    public void createTreatment(String doctorId, Treatment treatment, OnResponse<Void> cb) {
        treatmentApi.createPrescription(treatment.getPrescription()).enqueue(new OnResponse<Prescription>() {
            @Override
            public void onRequestSuccess(Prescription response) {
                Map<String, String> data = new HashMap<>();
                data.put("doctor", doctorId);
                data.put("patient", treatment.getPatient().getId());
                data.put("service", treatment.getServicePackId());
                data.put("time", treatment.getTime());
                data.put("date", treatment.getDate());
                data.put("note", treatment.getNote());
                data.put("prescription", response.getId());

                treatmentApi.createTreatment(data).enqueue(cb);
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                cb.onRequestFailed(response);
            }
        });
    }
}
