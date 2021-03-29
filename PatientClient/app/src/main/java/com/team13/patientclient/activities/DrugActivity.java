package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.services.DrugService;

public class DrugActivity extends AppCompatActivity {
    MaterialToolbar topAppBar;
    DrugModel medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        String id = getIntent().getStringExtra("id");

        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void renderingData(String id){
        DrugService service = new DrugService();

    }
}