package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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
        TextView service = findViewById(R.id.treatment_service);
        TextView time = findViewById(R.id.treatment_time);
        TextView status = findViewById(R.id.treatment_status);
        ExtendedFloatingActionButton prescriptionButton = findViewById(R.id.treatment_prescription);

        doctor.setText(treatment.getDoctorName());
        service.setText(treatment.getServicePack());
        time.setText(treatment.getTime() + ", " + treatment.getDate());
        status.setText(treatment.getStatus());
        if(treatment.getPrescription()!=null){
            prescriptionButton.setOnClickListener(v->{
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(treatment.getPrescription());
                assert getSupportFragmentManager()!=null;
                fragment.show(getSupportFragmentManager(),fragment.getTag());
            });
        } else {
            prescriptionButton.setVisibility(View.GONE);
        }

    }
}