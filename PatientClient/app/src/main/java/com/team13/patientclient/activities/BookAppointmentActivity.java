package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.AppointmentConfirmFragment;
import com.team13.patientclient.activities.fragments.ReasonPickFragment;
import com.team13.patientclient.activities.fragments.SchedulePickFragment;

public class BookAppointmentActivity extends AppCompatActivity implements
        SchedulePickFragment.SchedulePickFragmentListener,
        ReasonPickFragment.ReasonPickFragmentListener,
        AppointmentConfirmFragment.AppointmentConfirmListener {
    String time;
    String serviceName;
    String reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        loadFragment(new SchedulePickFragment());
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        Intent i = getIntent();
        serviceName = i.getStringExtra("Service");
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void gotoReasonPick(String time) {
        this.time = time;
        loadFragment(new ReasonPickFragment());
    }

    @Override
    public void gotoAppointmentConfirm(String reason) {
        this.reason = reason;
        loadFragment(new AppointmentConfirmFragment());
    }

    @Override
    public String getSelectedTime() {
        return this.time;
    }

    @Override
    public String getSelectedService() {
        return this.serviceName;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public void handleConfirm() {
        // Handle processing when click appointment confirm button
        //*** TODO ***//
    }
}