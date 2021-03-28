package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.MedicineModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.MedicineApi;

public class MedicineService {
    MedicineApi api = RetrofitSingleton.getInstance().create(MedicineApi.class);

    public void getMinimizedMedicineData(OnResponse<MedicineModel[]> callback) { api.get().enqueue(callback);}
}
