package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.models.Doctor;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.DoctorService;

public class DoctorDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

        String id = getIntent().getStringExtra("id");
        if (id != null){
            DoctorService service = new DoctorService();
            service.getDoctorById(id, new OnSuccessResponse<Doctor>() {
                @SuppressLint("NewApi")
                @Override
                public void onSuccess(Doctor data) {
                    ((TextView) findViewById(R.id.doctor_name)).setText(data.getDoctorName());
                    Picasso.get().load(data.getAvatarUrl()).into((ImageView) findViewById(R.id.doctor_avatar));
                    ((TextView) findViewById(R.id.doctor_bio)).setText(data.getBio());
                    String[] departments = data.getDepartment().split(",");
                    LinearLayout container = findViewById(R.id.departments);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 10,0, 10);

                    for (String department : departments){
                        Chip chip = new Chip(DoctorDetailActivity.this);
                        chip.setLayoutParams(params);
                        chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        chip.setText(department);
                        container.addView(chip);
                    }
                }
            });
        }
    }
}