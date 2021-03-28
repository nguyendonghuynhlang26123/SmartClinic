package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;

public class BookAppointmentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment_dashboard);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        final TextView[] service = new TextView[1];
        findViewById(R.id.general_care_card).setOnClickListener(v->{
            service[0] = findViewById(R.id.general_care_label);
            Intent i = new Intent(this, BookAppointmentActivity.class);
            i.putExtra("Service",service[0].getText().toString());
            startActivity(i);
        });
        findViewById(R.id.pediatric_card).setOnClickListener(v->{
            service[0] = findViewById(R.id.pediatric_label);
            Intent i = new Intent(this, BookAppointmentActivity.class);
            i.putExtra("Service",service[0].getText().toString());
            startActivity(i);
        });
        findViewById(R.id.maternity_card).setOnClickListener(v->{
            service[0] = findViewById(R.id.maternity_label);
            Intent i = new Intent(this, BookAppointmentActivity.class);
            i.putExtra("Service",service[0].getText().toString());
            startActivity(i);
        });
        findViewById(R.id.location_button).setOnClickListener(v->{
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        });
        findViewById(R.id.all_service_button).setOnClickListener(v->{
            Intent i = new Intent(this, ServiceActivity.class);
            startActivity(i);
        });
    }
}