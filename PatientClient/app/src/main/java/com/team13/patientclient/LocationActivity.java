package com.team13.patientclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

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