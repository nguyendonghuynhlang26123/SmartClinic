package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.fragments.AppointmentDetailFragment;
import com.team13.doctorclient.activities.fragments.PrescriptionDisplayFragment;
import com.team13.doctorclient.adapters.TreatmentTimelineAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.models.PatientModel;
import com.team13.doctorclient.models.PatientPageModel;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.models.Treatment;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;
import com.team13.doctorclient.repositories.services.PatientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PatientDetailActivity extends AppCompatActivity {
    TreatmentTimelineAdapter treatmentTimelineAdapter;
    RecyclerView patientTreatmentTimeline;
    MaterialToolbar topAppBar;
    Button startBtn;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        status = getIntent().getStringExtra("status");

        //Setup topbar
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

        //Setup Recycle view
        treatmentTimelineAdapter = new TreatmentTimelineAdapter(this);
        treatmentTimelineAdapter.setListener(treatment -> {
            PrescriptionDisplayFragment fragment = PrescriptionDisplayFragment.newInstance(treatment.getPrescription(), treatment.getPatient());
            fragment.show(getSupportFragmentManager(), fragment.getTag());
        });
        patientTreatmentTimeline = findViewById(R.id.treatment_timeline);
        patientTreatmentTimeline.setAdapter(treatmentTimelineAdapter);
        patientTreatmentTimeline.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        if(status.equals("START")){
            handleStart();

        }
        else if(status.equals("checkin")){
            handleCheckinMode();
        }
        else if (status.equals("REVIEW")){
            handleReviewMode();
        }

    }

    private void handleReviewMode() {
        PatientModel patient = (PatientModel) getIntent().getSerializableExtra("patient");
        if (patient == null) return;

        Picasso.get().load(patient.getAvatarUrl()).into((ImageView)findViewById(R.id.patient_img));
        ((TextView)findViewById(R.id.patient_name)).setText(patient.getName());

        callApiAndRenderData(patient.getId());
    }

    private void handleStart() {
        Appointment appointment =(Appointment) getIntent().getSerializableExtra("appointment");
        if (appointment == null) return;

        //Setup image
        Picasso.get().load(appointment.getPatient().getAvatarUrl()).into((ImageView)findViewById(R.id.patient_img));
        ((TextView)findViewById(R.id.patient_name)).setText(appointment.getPatient().getName());

        loadFragment(R.id.appointment_container, AppointmentDetailFragment.newInstance(appointment));
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        startBtn= findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent= new Intent(this, NewPrescriptionActivity.class);
            intent.putExtra("appointment", appointment);
            this.startActivity(intent);
        });

        //Call api
        callApiAndRenderData(appointment.getPatientId());
    }

    private void handleCheckinMode() {
        Appointment appointment =(Appointment) getIntent().getSerializableExtra("appointment");
        if (appointment == null) return;

        Picasso.get().load(appointment.getPatient().getAvatarUrl()).into((ImageView)findViewById(R.id.patient_img));
        ((TextView)findViewById(R.id.patient_name)).setText(appointment.getPatient().getName());

        loadFragment(R.id.appointment_container, AppointmentDetailFragment.newInstance(appointment));
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        startBtn = findViewById(R.id.startBtn);
        startBtn.setText("Check in");
        startBtn.setOnClickListener(v -> {
            //Set processing dialog
            ProgressDialog dialog = new ProgressDialog(PatientDetailActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Processing data! Please wait a minute.");
            dialog.show();

            //Setup dialog for showing result:
            AlertDialog alertDialog = new AlertDialog.Builder(PatientDetailActivity.this).create();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog1, which) -> {
                dialog1.dismiss();
                finish();
            });

            //Call APis
            AppointmentService service = new AppointmentService();
            HashMap<String, String > params = new HashMap<>();
            params.put("status", Utils.STATUS_PROCESSING);
            service.updateAnAppointment(appointment.getId(), params, new OnResponse<Void>() {
                @Override
                public void onRequestSuccess(Void response) {
                    alertDialog.setMessage("Check-in succeeded!");
                    alertDialog.show();
                }

                @Override
                public void onRequestFailed(ErrorResponse response) {
                    alertDialog.setMessage("Check-in failed! Please try again");
                    alertDialog.show();
                }
            });
        });

        //Call api
        callApiAndRenderData(appointment.getPatientId());
    }

    void callApiAndRenderData(String patientId){
        findViewById(R.id.progress).setVisibility(View.VISIBLE);

        // Get data from server
        PatientService patientService = new PatientService();
        patientService.getMedicalHistory(patientId, new OnSuccessResponse<Treatment[]>() {
            @Override
            public void onSuccess(Treatment[] response) {
                treatmentTimelineAdapter.setData(new ArrayList<>(Arrays.asList(response)));
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        });
    }


    private void loadFragment(int id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}