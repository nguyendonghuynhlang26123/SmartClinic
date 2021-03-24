package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;

public class CommonServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_service);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());
    }
}