package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Drug;
import com.team13.doctorclient.models.Prescription;

import java.util.ArrayList;

public class PrescriptionViewActivity extends AppCompatActivity {
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    MaterialToolbar topAppBar;
    TextView patientName, patientSymptom, patientDiagnostic, patientDayStart,patientDayEnd;
    Prescription prescription;
    String idPrescription;
    ArrayList<Drug> drugs= new ArrayList<Drug>(10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription__view_);
        patientName=findViewById(R.id.prescription_patient_name);
        patientSymptom=findViewById(R.id.prescription_patient_symptom);
        patientDiagnostic= findViewById(R.id.prescription_patient_diagnostic);
        patientDayStart=findViewById(R.id.prescription_patient_date);
        patientDayEnd=findViewById(R.id.prescription_patient_date_end);
        drugList= findViewById(R.id.drug_list);
        topAppBar= findViewById(R.id.topAppBar);
        Intent i= getIntent();
        idPrescription= i.getStringExtra("IdPrescription");
        renderData(idPrescription);
    }

    private void renderData(String idPrescription){
        // TODO
        prescription= new Prescription("001","Mn ",getDrug(drugs),"note here","cough","cough","8/04/2021","15/04/2021");

        patientName.setText(prescription.getPatientName());
        patientSymptom.setText(prescription.getSymptom());
        patientDiagnostic.setText(prescription.getDiagnose());
        patientDayStart.setText(prescription.getDateStart());
        patientDayEnd.setText(prescription.getDateEnd());

        topAppBar.setNavigationOnClickListener(v -> finish());
        drugAdapter =new DrugAdapter(this,prescription.getDrugList());
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    //TODO
    public ArrayList<Drug> getDrug(ArrayList<Drug> drugArrayList){
        for(int i=0;i<10;++i){
            drugArrayList.add(new Drug("001","Panadol","3","Ngày 2 lần"));
        }
        return drugArrayList;
    }
}