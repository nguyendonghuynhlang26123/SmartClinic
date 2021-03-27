package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.Drug;

import java.util.ArrayList;

public class PharmacyActivity extends AppCompatActivity {
    RecyclerView commonDrugList;
    RecyclerView insuranceDrugList;
    RecyclerView othersDrugList;
    MaterialToolbar topAppBar;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.cart) {
                Intent i = new Intent(this, Cart.class);
                startActivity(i);
                return true;
            }
            return false;
        });
        commonDrugList = findViewById(R.id.common_drug);
        PharmacyItemAdapter pharmacyItemAdapter = new PharmacyItemAdapter(this, getDepartments());
        commonDrugList.setAdapter(pharmacyItemAdapter);
        commonDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        insuranceDrugList = findViewById(R.id.insurance_drug);
        insuranceDrugList.setAdapter(pharmacyItemAdapter);
        insuranceDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        othersDrugList = findViewById(R.id.others_drug);
        othersDrugList.setAdapter(pharmacyItemAdapter);
        othersDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    ArrayList<Drug> getDepartments(){
        ArrayList<Drug> drugs = new ArrayList<>(7);
        for(int i=0;i<7;++i){
            drugs.add(new Drug("dep"));
        }
        return drugs;
    }
}