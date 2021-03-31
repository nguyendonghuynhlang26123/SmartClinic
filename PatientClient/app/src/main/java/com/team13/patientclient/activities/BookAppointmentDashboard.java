package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.adapters.ServicePackAdapter;
import com.team13.patientclient.models.HospitalModel;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.HospitalService;
import com.team13.patientclient.repository.services.ServicePackService;

import java.util.ArrayList;
import java.util.Arrays;

public class BookAppointmentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment_dashboard);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        RecyclerView serviceList = findViewById(R.id.service_list);

        //rendering dumb data while waiting for response from server
        ServicePackAdapter servicePackAdapter = new ServicePackAdapter(this, getEmptyModels(3));
        serviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceList.setAdapter(servicePackAdapter);

        //Render hospital data
        HospitalModel hospital = Store.get_instance().getHospital();
        if (!hospital.getImgUrl().isEmpty()) Picasso.get().load(hospital.getImgUrl()).into((ImageView)findViewById(R.id.hospital_thumbnail));

        ((TextView) findViewById(R.id.hospital_name)).setText(hospital.getName());
        ((TextView) findViewById(R.id.hospital_address)).setText(hospital.getAddress());
        ((TextView) findViewById(R.id.hospital_phone)).setText(hospital.getPhone());

        String workingHours = hospital.getDayOfWorks() + ", " + hospital.getOpenTime() + " - " + hospital.getCloseTime();
        ((TextView) findViewById(R.id.hospital_working_time)).setText(workingHours);

        callApiAndRender(servicePackAdapter);

        findViewById(R.id.all_service_button).setOnClickListener(l -> {
            Intent i = new Intent(BookAppointmentDashboard.this, ServiceActivity.class);
            startActivity(i);
        });
        findViewById(R.id.location_button).setOnClickListener(l->{
            Intent i = new Intent(BookAppointmentDashboard.this, LocationActivity.class);
            startActivity(i);
        });
    }

    private void callApiAndRender(ServicePackAdapter servicePackAdapter) {
        ServicePackService service = new ServicePackService();
        service.get(3, new OnResponse<ServicePack[]>() {
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