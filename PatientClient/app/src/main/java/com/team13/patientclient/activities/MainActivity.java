package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.AppointmentFragment;
import com.team13.patientclient.activities.fragments.BlogFragment;
import com.team13.patientclient.activities.fragments.HomeFragment;
import com.team13.patientclient.activities.fragments.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener {
    BottomNavigationView bottomNavigationView;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.blog:
                    fragment = new BlogFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.appointment:
                    fragment = new AppointmentFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    fragment = new ProfileFragment();
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

    @Override
    public void goToAppoinment() {
        loadFragment(new AppointmentFragment());
        bottomNavigationView.setSelectedItemId(R.id.appointment);
    }

    @Override
    public void gotoBlog() {
        loadFragment(new BlogFragment());
        bottomNavigationView.setSelectedItemId(R.id.blog);
    }
}