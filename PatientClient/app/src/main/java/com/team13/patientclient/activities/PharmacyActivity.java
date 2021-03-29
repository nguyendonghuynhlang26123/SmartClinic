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
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.DrugService;

import java.util.ArrayList;
import java.util.Arrays;

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
        renderingMedicineList();
    }

    private void renderingMedicineList() {
        DrugService drugService = new DrugService();
        DrugModel[] emptyModels = getEmptyModel(5);

        //Rendering empty data while waiting for response from server
        commonDrugList = findViewById(R.id.common_drug);
        PharmacyItemAdapter pharmacyItemAdapter = new PharmacyItemAdapter(PharmacyActivity.this, new ArrayList<>(Arrays.asList(emptyModels)));
        commonDrugList.setAdapter(pharmacyItemAdapter);
        commonDrugList.setLayoutManager(new LinearLayoutManager(PharmacyActivity.this, LinearLayoutManager.HORIZONTAL, false));

        insuranceDrugList = findViewById(R.id.insurance_drug);
        insuranceDrugList.setAdapter(pharmacyItemAdapter);
        insuranceDrugList.setLayoutManager(new LinearLayoutManager(PharmacyActivity.this, LinearLayoutManager.HORIZONTAL, false));

        othersDrugList = findViewById(R.id.others_drug);
        othersDrugList.setAdapter(pharmacyItemAdapter);
        othersDrugList.setLayoutManager(new LinearLayoutManager(PharmacyActivity.this, LinearLayoutManager.HORIZONTAL, false));

        drugService.getMinimizedData(new OnResponse<DrugModel[]>() {
            @Override
            public void onRequestSuccess(DrugModel[] list) {
                pharmacyItemAdapter.setData(new ArrayList<>(Arrays.asList(list)));
            }
        });
    }

    private DrugModel[] getEmptyModel(int n) {
        DrugModel[] returnData = new DrugModel[n];

        for (int i = 0; i < n; i++) {
            returnData[i] = new DrugModel();
        }
        return returnData;
    }
}