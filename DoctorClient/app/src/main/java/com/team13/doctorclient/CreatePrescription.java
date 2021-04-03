package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Drug;

import java.util.ArrayList;

public class CreatePrescription extends AppCompatActivity implements DrugAddFragment.AddDrugListener {
    MaterialToolbar topAppBar;
    BottomAppBar bottomAppBar;
    FloatingActionButton addDrug;
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    ArrayList<Drug> addDrugArrayList=new ArrayList<>(5);
    DrugAddFragment drugAddFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);
        topAppBar= findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        bottomAppBar=findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });
        addDrug= findViewById(R.id.add_drugBtn);
        addDrug.setOnClickListener(v -> {
            drugAddFragment=new DrugAddFragment();
            assert getSupportFragmentManager()!=null;
            drugAddFragment.show(getSupportFragmentManager(),drugAddFragment.getTag());
        });
        drugList= findViewById(R.id.drug_list);
        drugAdapter= new DrugAdapter(this);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }


    @Override
    public void onSaveDrug(Drug addDrug) {
        addDrugArrayList.add(addDrug);
        drugAdapter.setData(addDrugArrayList);
        drugAddFragment.dismiss();
    }
}