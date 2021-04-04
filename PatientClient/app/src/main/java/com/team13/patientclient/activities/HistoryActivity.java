package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.PrescriptionFragment;
import com.team13.patientclient.adapters.TreatmentAdapter;
import com.team13.patientclient.models.DrugDetail;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v->finish());
        RecyclerView treatmentList = findViewById(R.id.treatment_list);
        TreatmentAdapter adapter = new TreatmentAdapter(this, new TreatmentAdapter.TreatmentItemListener() {
            @Override
            public void onItemClick(Treatment.Prescription prescription) {
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(prescription);
                fragment.show(getSupportFragmentManager(),fragment.getTag());
            }

            @Override
            public void onHasAppointment() {

            }

            @Override
            public void onAppointmentRemove() {

            }
        }, getEmptyTreatment());
        treatmentList.setAdapter(adapter);
        treatmentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    ArrayList<Treatment> getEmptyTreatment(){
        ArrayList<Treatment> treatments = new ArrayList<>(8);
        treatments.add(new Treatment("1","General Care","5/1/2021","17:30","A","Dr.Coco","B","MN","PENDING"));
        treatments.add(new Treatment("1","General Care","4/1/2021","17:30","A","Dr.Coco","B","MN","CANCEL"));
        treatments.add(new Treatment("1","General Care","3/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("1","General Care","2/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("1","General Care","1/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("1","General Care","31/12/2020","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        ArrayList<DrugDetail> testDrugList= new ArrayList<DrugDetail>(Collections.singletonList(new DrugDetail(new DrugModel(), 2, "Morning: 2")));
        for(Treatment treatment: treatments){
            treatment.createPrescription(testDrugList,"Comeback at 3/3/2021","No","Sleep");
        }
        return treatments;
    }
}