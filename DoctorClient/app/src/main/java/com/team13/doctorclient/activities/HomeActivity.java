package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team13.doctorclient.activities.fragments.DoctorForumFragment;
import com.team13.doctorclient.activities.fragments.DoctorProfileFragment;
import com.team13.doctorclient.activities.fragments.MedicalRecordFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.activities.fragments.ScheduleFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new ScheduleFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.schedule);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.blog:
                    fragment = new DoctorForumFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.schedule:
                    fragment = new ScheduleFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.medical_record:
                    fragment= new MedicalRecordFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    fragment= new DoctorProfileFragment();
                    loadFragment(fragment);
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
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}