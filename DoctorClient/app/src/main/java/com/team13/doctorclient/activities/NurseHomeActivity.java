package com.team13.doctorclient.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.R;
import com.team13.doctorclient.activities.fragments.AppointmentDetailFragment;
import com.team13.doctorclient.activities.fragments.DoctorBlogFragment;
import com.team13.doctorclient.activities.fragments.DoctorProfileFragment;
import com.team13.doctorclient.activities.fragments.MedicalRecordFragment;
import com.team13.doctorclient.activities.fragments.NurseScheduleFragment;
import com.team13.doctorclient.activities.fragments.QRFragment;
import com.team13.doctorclient.activities.fragments.ScheduleFragment;
import com.team13.doctorclient.adapters.PendingAppointmentAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.util.ArrayList;
import java.util.Arrays;

public class NurseHomeActivity extends AppCompatActivity  implements QRFragment.Listener{
    BottomNavigationView bottomNavigationView;
    int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        currentId = R.id.schedule;
        loadFragment(new NurseScheduleFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.schedule);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.schedule:
                    if (currentId == R.id.schedule) return false;
                    loadFragment( new NurseScheduleFragment());
                    currentId = R.id.schedule;
                    return true;
                case R.id.qrscan:
                    if (currentId == R.id.qrscan) return false;
                    loadFragment( new QRFragment());
                    currentId = R.id.qrscan;
                    return true;
                default:
                    return false;
            }
        });

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.blog);
        badge.setVisible(true);
        badge.setNumber(99);
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onVerifySucceed(Appointment appointment) {
        Fragment fragment = AppointmentDetailFragment.newInstance(appointment);
        loadFragment(fragment);
    }
}