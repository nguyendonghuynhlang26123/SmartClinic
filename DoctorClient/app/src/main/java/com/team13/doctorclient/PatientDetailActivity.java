package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.adapters.TreatmentTimelineAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class PatientDetailActivity extends AppCompatActivity {
    TreatmentTimelineAdapter treatmentTimelineAdapter;
    RecyclerView patientTreatmentTimeline;
    MaterialToolbar topAppBar;
    Button startBtn;
    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        Intent i = getIntent();
        appointment =(Appointment) i.getSerializableExtra("appointment");
        patientTreatmentTimeline = findViewById(R.id.patient_treatment_timeline);
        treatmentTimelineAdapter = new TreatmentTimelineAdapter(this,getTreatmentTimeline());
        patientTreatmentTimeline.setAdapter(treatmentTimelineAdapter);
        patientTreatmentTimeline.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());
        TextView patientTime = findViewById(R.id.patient_time);
        patientTime.setText(appointment.getTime()+" "+appointment.getDate());
        TextView patientTreatment = findViewById(R.id.patient_treatment);
        patientTreatment.setText(appointment.getService().getName());
        //TODO: Money
        startBtn= findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent= new Intent(this, NewPrescriptionActivity.class);
            intent.putExtra("appointment", appointment);
            this.startActivity(intent);
        });

    }
    ArrayList<Treatment> getTreatmentTimeline(){
        ArrayList<Treatment> treatments = new ArrayList<>(10);
        ServicePack servicePack = new ServicePack("Beauty Care","no", 500000, "1");
        Appointment appointment = new Appointment("MN", servicePack, "Cough","7/04/2021","13:30","PROCESSING");
        Prescription prescription = new Prescription();
        treatments.add(new Treatment(appointment,prescription));
        treatments.add(new Treatment(appointment,prescription));
        treatments.add(new Treatment(appointment,prescription));
        return treatments;
    }
}