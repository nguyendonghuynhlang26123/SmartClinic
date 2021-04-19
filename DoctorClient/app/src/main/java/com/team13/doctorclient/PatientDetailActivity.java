package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.adapters.PatientTimelineAdapter;
import com.team13.doctorclient.models.DoctorTimeline;
import com.team13.doctorclient.models.Drug;
import com.team13.doctorclient.models.PatientTimeline;

import java.util.ArrayList;

public class PatientDetailActivity extends AppCompatActivity {
    PatientTimelineAdapter patientTimelineAdapter;
    RecyclerView patientTimeline;
    MaterialToolbar topAppBar;
    Button startBtn;
    DoctorTimeline
            doctorTimeline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        Intent i = getIntent();
        doctorTimeline =(DoctorTimeline)i.getSerializableExtra("timeline");
        patientTimeline=findViewById(R.id.patient_treatment_timeline);
        patientTimelineAdapter= new PatientTimelineAdapter(this,getPatientTimeline());
        patientTimeline.setAdapter(patientTimelineAdapter);
        patientTimeline.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());
        TextView patientTime = findViewById(R.id.patient_time);
        patientTime.setText(doctorTimeline.getTime());
        TextView patientTreatment = findViewById(R.id.patient_treatment);
        patientTreatment.setText(doctorTimeline.getTreatment());
        //TODO: Money
        startBtn= findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent= new Intent(this,CreatePrescription.class);
            intent.putExtra("patient",doctorTimeline);
            this.startActivity(intent);
        });

    }
    ArrayList<PatientTimeline> getPatientTimeline(){
        ArrayList<PatientTimeline> patientTimelineArrayList= new ArrayList<>(10);
        patientTimelineArrayList.add(new PatientTimeline("001","MN","001","Ho, sốt","viêm hong cấp tính","general","7/04/2021","13:30"));
        patientTimelineArrayList.add(new PatientTimeline("001","MN","001","Ho, sốt","viêm hong cấp tính","general","7/04/2021","13:30"));
        patientTimelineArrayList.add(new PatientTimeline("001","MN","001","Ho, sốt","viêm hong cấp tính","general","7/04/2021","13:30"));
        return patientTimelineArrayList;
    }
}