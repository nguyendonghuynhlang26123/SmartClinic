package com.team13.doctorclient.repositories.services;

import com.team13.doctorclient.models.TreatmentDTO;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.TreatmentApi;


public class TreatmentService {
    TreatmentApi treatmentApi = RetrofitSingleton.getInstance().create(TreatmentApi.class);

    public void createTreatment(TreatmentDTO data, OnResponse<Void> cb) {
        treatmentApi.registerTreatment(data).enqueue(cb);
    }
}
