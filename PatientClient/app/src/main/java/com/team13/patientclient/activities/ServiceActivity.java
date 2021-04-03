package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.ServiceDisplayFragment;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.ServicePackService;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceActivity extends AppCompatActivity implements ServiceDisplayFragment.ServiceDisplayListener {
    ArrayList<ServicePack> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

        //rendering dumb data while waiting for response from server
//        ServicePackAdapter servicePackAdapter = new ServicePackAdapter(this, getEmptyModels(10));
//        serviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        serviceList.setAdapter(servicePackAdapter);

        callApiAndRender();
    }

    private void callApiAndRender() {
        ServicePackService service = new ServicePackService();
        loadFragment(new ProgressFragment());
        service.get(new OnSuccessResponse<ServicePack[]>() {
            @Override
            public void onSuccess(ServicePack[] list) {
                //Rendering data as soon as it received
//                servicePackAdapter.setData(new ArrayList<>(Arrays.asList(list)));
                data = new ArrayList<>(Arrays.asList(list));
                loadFragment(new ServiceDisplayFragment());
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

    @Override
    public ArrayList<ServicePack> getDisplayService() {
        return data;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}