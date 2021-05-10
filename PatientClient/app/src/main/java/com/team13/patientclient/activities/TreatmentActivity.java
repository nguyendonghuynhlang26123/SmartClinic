package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
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
        time.setText(String.format("%s, %s", treatment.getTime(), treatment.getDate()));
        status.setText(treatment.getStatus());
        if(treatment.getPrescription()!=null){
            prescriptionButton.setOnClickListener(v->{
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(treatment.getPrescription());
                fragment.show(getSupportFragmentManager(),fragment.getTag());
            });
        } else {
            prescriptionButton.setVisibility(View.GONE);
            findViewById(R.id.treatment_qrlayout).setVisibility(View.VISIBLE);
            ImageView qrImage = findViewById(R.id.treatment_qrcode);
            String appointmentId = treatment.getAppointment().getId();
            String patientId = Store.get_instance().getPatientId();
            findViewById(R.id.treatment_qr_loading).setVisibility(View.VISIBLE);
            if (appointmentId != null){
                String url = "https://api.qrserver.com/v1/create-qr-code/?size=350x350&data=" + appointmentId + "-" + patientId;
                Picasso.get().load(url).into(qrImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        findViewById(R.id.treatment_qr_loading).setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        findViewById(R.id.treatment_qr_loading).setVisibility(View.GONE);
                        findViewById(R.id.treatment_failed).setVisibility(View.VISIBLE);
                    }
                });
            }
        }

    }
}