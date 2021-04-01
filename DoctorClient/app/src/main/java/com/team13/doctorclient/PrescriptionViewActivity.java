package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.team13.doctorclient.adapters.DurgAdapter;
import com.team13.doctorclient.models.Drug;

import java.util.ArrayList;

public class PrescriptionViewActivity extends AppCompatActivity {
    RecyclerView drugList;
    DurgAdapter durgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription__view_);
        drugList= findViewById(R.id.drug_list);
        durgAdapter=new DurgAdapter(this,getDrug());
        drugList.setAdapter(durgAdapter);
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