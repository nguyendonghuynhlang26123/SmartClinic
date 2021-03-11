package com.team13.patientclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

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
        DepartmentItemAdapter departmentItemAdapter1 = new DepartmentItemAdapter(this, getDepartments());
        commonDrugList.setAdapter(departmentItemAdapter1);
        commonDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        insuranceDrugList = findViewById(R.id.insurance_drug);
        insuranceDrugList.setAdapter(departmentItemAdapter1);
        insuranceDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        othersDrugList = findViewById(R.id.others_drug);
        othersDrugList.setAdapter(departmentItemAdapter1);
        othersDrugList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    ArrayList<Department> getDepartments(){
        ArrayList<Department> departments = new ArrayList<>(7);
        for(int i=0;i<7;++i){
            departments.add(new Department("dep"));
        }
        return departments;
    }
}