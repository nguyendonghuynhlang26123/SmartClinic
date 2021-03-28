package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.adapters.ServicePackAdapter;
import com.team13.patientclient.models.ServicePack;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());
        RecyclerView serviceList = findViewById(R.id.service_list);
        ServicePackAdapter servicePackAdapter = new ServicePackAdapter(this, getServices());
        serviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceList.setAdapter(servicePackAdapter);
    }
    ArrayList<ServicePack> getServices(){
        ArrayList<ServicePack> servicePacks = new ArrayList<>(10);
        servicePacks.add(new ServicePack("General Care",R.drawable.ic_doctor,500000));
        servicePacks.add(new ServicePack("Pediatric",R.drawable.ic_doctor,500000));
        servicePacks.add(new ServicePack("Maternity Full Exam",R.drawable.ic_doctor,2500000));
        servicePacks.add(new ServicePack("Annual Health Check Up",R.drawable.ic_doctor,3000000));
        servicePacks.add(new ServicePack("Cardiology",R.drawable.ic_doctor,600000));
        servicePacks.add(new ServicePack("Endocrinology",R.drawable.ic_doctor,600000));
        servicePacks.add(new ServicePack("Vaccine Consultation",R.drawable.ic_doctor,500000));
        servicePacks.add(new ServicePack("Ear, Nose, Throat",R.drawable.ic_doctor,500000));
        servicePacks.add(new ServicePack("Eye Care",R.drawable.ic_doctor,500000));
        servicePacks.add(new ServicePack("Maternity Consultation",R.drawable.ic_doctor,1000000));
        return servicePacks;
    }
}