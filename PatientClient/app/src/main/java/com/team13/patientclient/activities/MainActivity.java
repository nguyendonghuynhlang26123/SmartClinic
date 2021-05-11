package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.AppointmentFragment;
import com.team13.patientclient.activities.fragments.ForumFragment;
import com.team13.patientclient.activities.fragments.HomeFragment;
import com.team13.patientclient.activities.fragments.ProfileEditFragment;
import com.team13.patientclient.activities.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, ProfileEditFragment.ProfileEditListener {
    BottomNavigationView bottomNavigationView;
    int currentId;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentId = -1;


        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> navigationHandle(item.getItemId()));
        handleInitialNavigation();
//        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.blog);
//        badge.setVisible(true);
//        badge.setNumber(99);
    }

    private boolean navigationHandle(int itemId) {

        switch (itemId){
            case R.id.home:
                if (currentId == R.id.home) return false;
                loadFragment( new HomeFragment());
                currentId = R.id.home;
                return true;
            case R.id.blog:
                if (currentId == R.id.blog) return false;
                loadFragment(new ForumFragment());
                currentId = R.id.blog;
                return true;
            case R.id.appointment:
                if (currentId == R.id.appointment) return false;
                loadFragment(new AppointmentFragment());
                currentId = R.id.appointment;
                return true;
            case R.id.profile:
                if (currentId == R.id.profile) return false;
                loadFragment(new ProfileFragment());
                currentId = R.id.profile;
                return true;
            default:
                return false;
        }
    }

    private void handleInitialNavigation() {
        Intent intent = getIntent();
        int navId = intent.getIntExtra("navigation", R.id.home);
        bottomNavigationView.setSelectedItemId(navId);
        navigationHandle(navId);

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
    public void gotoBlog() {
        loadFragment(new ForumFragment());
        bottomNavigationView.setSelectedItemId(R.id.blog);
    }


    @Override
    public void onUpdateSucceed() {
        loadFragment(new ProfileFragment());
    }
}