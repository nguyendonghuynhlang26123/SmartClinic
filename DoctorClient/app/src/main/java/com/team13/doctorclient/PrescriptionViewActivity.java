package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Drug;

import java.util.ArrayList;

public class PrescriptionViewActivity extends AppCompatActivity {
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription__view_);
        drugList= findViewById(R.id.drug_list);
        topAppBar= findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        drugAdapter =new DrugAdapter(this,getDrug());
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }
    public ArrayList<Drug> getDrug(){
        ArrayList<Drug> drugArrayList= new ArrayList<>(10);
        for(int i=0;i<10;++i){
            drugArrayList.add(new Drug("001","Panadol","3","Ngày 2 lần"));
        }
        return drugArrayList;
    }
}