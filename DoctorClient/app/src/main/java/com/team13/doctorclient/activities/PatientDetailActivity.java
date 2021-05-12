package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.NewPrescriptionActivity;
import com.team13.doctorclient.activities.fragments.AppointmentDetailFragment;
import com.team13.doctorclient.adapters.TreatmentTimelineAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.models.Treatment;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientDetailActivity extends AppCompatActivity {
    TreatmentTimelineAdapter treatmentTimelineAdapter;
    RecyclerView patientTreatmentTimeline;
    MaterialToolbar topAppBar;
    Button startBtn;
    Appointment appointment;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        Intent i = getIntent();
        appointment =(Appointment) i.getSerializableExtra("appointment");
        status = i.getStringExtra("status");

        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

        if(status.equals("START")){
            loadFragment(R.id.appointment_container, AppointmentDetailFragment.newInstance(appointment));
            findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            startBtn= findViewById(R.id.startBtn);
            startBtn.setOnClickListener(v -> {
                Intent intent= new Intent(this, NewPrescriptionActivity.class);
                intent.putExtra("appointment", appointment);
                this.startActivity(intent);
            });
        }
        else if(status.equals("checkin")){
            loadFragment(R.id.appointment_container, AppointmentDetailFragment.newInstance(appointment));
            findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            startBtn= findViewById(R.id.startBtn);
            startBtn.setText("Check in");
            startBtn.setOnClickListener(v -> {
                ProgressDialog dialog = new ProgressDialog(PatientDetailActivity.this);
                dialog.setCancelable(false);
                dialog.setMessage("Processing data! Please wait a minute.");
                dialog.show();
                AppointmentService service = new AppointmentService();
                HashMap<String, String > params = new HashMap<>();
                params.put("status", Utils.STATUS_PROCESSING);
                AlertDialog alertDialog = new AlertDialog.Builder(PatientDetailActivity.this).create();
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog1, which) -> {
                    dialog1.dismiss();
                    finish();
                });
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
        }
        renderTreatmentTimeline();
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

    void renderTreatmentTimeline(){
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        // Get data from server
        patientTreatmentTimeline = findViewById(R.id.treatment_timeline);
        treatmentTimelineAdapter = new TreatmentTimelineAdapter(this,getTreatmentTimeline());
        patientTreatmentTimeline.setAdapter(treatmentTimelineAdapter);
        patientTreatmentTimeline.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    private void loadFragment(int id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}