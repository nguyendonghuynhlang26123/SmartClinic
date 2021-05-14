package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.fragments.DrugAddFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.activities.fragments.RescheduleFragment;
import com.team13.doctorclient.activities.fragments.ReviewPrescriptionFragment;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.DrugDetail;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.models.PatientModel;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.models.Treatment;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;
import com.team13.doctorclient.repositories.services.PatientService;
import com.team13.doctorclient.repositories.services.TreatmentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewPrescriptionActivity extends AppCompatActivity implements DrugAddFragment.AddDrugListener,
        RescheduleFragment.RescheduleListener, ReviewPrescriptionFragment.ReviewDrugListener {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    DrugAdapter drugAdapter;
    ArrayList<DrugDetail> drugDetails = new ArrayList<>(5);
    ArrayList<Treatment> medicalHistory;

    Calendar myCalendar;
    Appointment appointment;

    TextInputEditText symptom, diagnostic;
    TextView  endDay, note;

    int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);

        curIndex = 0;

        //Get Data
        Intent i = getIntent();
        appointment= (Appointment) i.getSerializableExtra("appointment");
        medicalHistory = (ArrayList<Treatment>) i.getSerializableExtra("treatments");

        myCalendar= Calendar.getInstance();

        symptom = findViewById(R.id.input_symptom);
        diagnostic=findViewById(R.id.input_diagnostic);
        endDay=findViewById(R.id.prescription_patient_date_end);
        note=findViewById(R.id.note_prescription);
        MaterialToolbar topAppBar= findViewById(R.id.topAppBar);
        BottomAppBar bottomAppBar=findViewById(R.id.bottom_app_bar);
        RecyclerView drugList= findViewById(R.id.drug_list);

        //Setup adapter
        drugAdapter= new DrugAdapter(this, true);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        //Set textviews
        ((TextView)findViewById(R.id.prescription_patient_name)).setText(appointment.getPatientName());
        ((TextView)findViewById(R.id.doctor_name)).setText("Dr." + Store.get_instance().getName());
        ((TextView)findViewById(R.id.prescription_patient_date_begin)).setText(appointment.getDate());
        symptom.setText(appointment.getNote());

        //Events
        topAppBar.setNavigationOnClickListener(v -> finish());
        //Pick date event
        findViewById(R.id.pick_date).setOnClickListener(v -> handlePickDate());
        // Finish + Reschedule event
        bottomAppBar.setOnMenuItemClickListener(item -> handleBottomBarEvent(item));
        // Review prescription event
        bottomAppBar.setNavigationOnClickListener(v-> handleReviewPrescription());
        // Add ( + ) button event
        findViewById(R.id.add_drugBtn).setOnClickListener(v -> handleAddADrug());
    }

    private void handleAddADrug() {
        DrugAddFragment drugAddFragment=new DrugAddFragment();
        drugAddFragment.show(getSupportFragmentManager(),drugAddFragment.getTag());
    }

    private void handleReviewPrescription() {
        ReviewPrescriptionFragment reviewPrescriptionFragment =  ReviewPrescriptionFragment.newInstance(medicalHistory.get(curIndex));
        reviewPrescriptionFragment.show(getSupportFragmentManager(),reviewPrescriptionFragment.getTag());
    }

    private void handlePickDate() {
        DatePickerDialog dialog=new DatePickerDialog(NewPrescriptionActivity.this, (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            endDay.setText(""+dayOfMonth+"/"+(month+1)+"/"+year);
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private boolean handleBottomBarEvent(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reschedule:
                return handleRescheduleEvent();
            case R.id.finish:
                //TODO
                handleSubmitResult();
                return false;
        }
        return false;
    }

    private void handleSubmitResult() {
        String sSymptom = symptom.getText().toString();
        String sDiagnostic = diagnostic.getText().toString();
        if (sDiagnostic.isEmpty() || sSymptom.isEmpty()) {
            Toast.makeText(NewPrescriptionActivity.this, "Please fill in both symptoms and diagnostic", Toast.LENGTH_LONG).show();
            return;
        }

        Prescription prescription = new Prescription(drugAdapter.getDrugs(), note.getText().toString(),sSymptom,sDiagnostic);
        Treatment data = new Treatment(appointment,prescription);

        callApi(data);
    }

    private void callApi(Treatment data) {
        TreatmentService service = new TreatmentService();
        ProgressDialog dialog = new ProgressDialog(NewPrescriptionActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Updating resources! Please wait for a few moment.");
        dialog.show();
        service.createTreatment(data.getDTO(Store.get_instance().getId(), appointment.getId()), new OnResponse<Void>() {
            @Override
            public void onRequestSuccess(Void response) {
                Toast.makeText(NewPrescriptionActivity.this, "Successfully register patient record!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                dialog.dismiss();
                Toast.makeText(NewPrescriptionActivity.this, "Failed when register patient record!" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleRescheduleEvent() {
        try {
            Date currentDate = format.parse(appointment.getDate() + " " + appointment.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.WEEK_OF_MONTH,1);
            String sNextWeek = format.format(calendar.getTime());

            RescheduleFragment fragment = RescheduleFragment.newInstance(sNextWeek);
            fragment.show(getSupportFragmentManager(),fragment.getTag());
        } catch (ParseException exception){
            return false;
        }
        return true;
    }


    @Override
    public void onDrugSaved(DrugDetail addDrug) {
        drugAdapter.appendData(addDrug);
    }

    @Override
    public void onApplyOldPrescription(ArrayList<DrugDetail> addDrugArrayList){
        drugAdapter.setData(addDrugArrayList);
    }

    @Override
    public Treatment getNextTreatment() {
        if (curIndex < medicalHistory.size()) curIndex++;
        return medicalHistory.get(curIndex);
    }

    @Override
    public Treatment getPreviousTreatment() {
        if (curIndex > 0) curIndex--;
        return medicalHistory.get(curIndex);
    }

    @Override
    public void setReschedule(Appointment appointment) {
        //***TODO: Send to db
        endDay.setText(appointment.getDate());
    }

}