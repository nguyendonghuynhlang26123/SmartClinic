package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.adapters.PatientTimelineAdapter;
import com.team13.doctorclient.models.PatientTimeline;

import java.util.ArrayList;

public class PatientDetailActivity extends AppCompatActivity {
    PatientTimelineAdapter patientTimelineAdapter;
    RecyclerView patientTimeline;
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        patientTimeline=findViewById(R.id.patient_treatment_timeline);
        patientTimelineAdapter= new PatientTimelineAdapter(this,getPatientTimeline());
        patientTimeline.setAdapter(patientTimelineAdapter);
        patientTimeline.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());
    }
    ArrayList<PatientTimeline> getPatientTimeline(){
        ArrayList<PatientTimeline> patientTimelineArrayList= new ArrayList<>(10);
        patientTimelineArrayList.add(new PatientTimeline("001","Ho, sốt","viêm hong cấp tính","001"));
        patientTimelineArrayList.add(new PatientTimeline("001","Ho, sốt","viêm hong cấp tính","001"));
        patientTimelineArrayList.add(new PatientTimeline("001","Ho, sốt","viêm hong cấp tính","001"));
        return patientTimelineArrayList;
    }
}