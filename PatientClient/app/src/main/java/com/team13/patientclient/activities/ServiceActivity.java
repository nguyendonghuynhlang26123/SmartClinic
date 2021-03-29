package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.adapters.ServicePackAdapter;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.ServicePackService;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

        RecyclerView serviceList = findViewById(R.id.service_list);

        //rendering dumb data while waitting for response from server
        ServicePackAdapter servicePackAdapter = new ServicePackAdapter(this, getEmptyModels(10));
        serviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceList.setAdapter(servicePackAdapter);

        callApiAndRender(servicePackAdapter);
    }

    private void callApiAndRender(ServicePackAdapter servicePackAdapter) {
        ServicePackService service = new ServicePackService();
        service.get(new OnResponse<ServicePack[]>() {
            @Override
            public void onRequestSuccess(ServicePack[] list) {
                //Rendering data as soon as it received
                servicePackAdapter.setData(new ArrayList<>(Arrays.asList(list)));
            }
        });
    }

    ArrayList<ServicePack> getEmptyModels(int n){
        ArrayList<ServicePack> servicePacks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            servicePacks.add(new ServicePack());
        }
        return servicePacks;
    }
}