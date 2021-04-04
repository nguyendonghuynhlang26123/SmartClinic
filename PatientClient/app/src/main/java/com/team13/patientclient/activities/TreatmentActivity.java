package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.PrescriptionFragment;
import com.team13.patientclient.models.Treatment;

public class TreatmentActivity extends AppCompatActivity {
    MaterialToolbar topAppBar;
    Treatment treatment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        Intent i = getIntent();
        treatment = (Treatment)i.getSerializableExtra("prescription");
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v->finish());
        TextView doctor = findViewById(R.id.treatment_doctor);
        doctor.setText(treatment.getDoctorName());
        TextView service = findViewById(R.id.treatment_service);
        service.setText(treatment.getServicePack());
        TextView time = findViewById(R.id.treatment_time);
        time.setText(treatment.getTime()+", "+treatment.getDate());
        findViewById(R.id.treatment_prescription).setOnClickListener(v->{
            PrescriptionFragment fragment = PrescriptionFragment.newInstance(treatment.getPrescription());
            assert getSupportFragmentManager()!=null;
            fragment.show(getSupportFragmentManager(),fragment.getTag());
        });
    }
}