package com.team13.patientclient.models;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;

public class Cart extends AppCompatActivity {
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
}