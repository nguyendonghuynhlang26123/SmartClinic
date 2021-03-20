package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.HospitalMapFragment;

public class LocationActivity extends AppCompatActivity {
    HospitalMapFragment hospitalMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        hospitalMapFragment = (HospitalMapFragment) fragmentManager.findFragmentById(R.id.fragment_map);
    }
}