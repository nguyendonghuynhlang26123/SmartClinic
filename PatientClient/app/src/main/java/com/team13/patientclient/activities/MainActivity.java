package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.team13.patientclient.MyDialog;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.MedicalRecordFragment;
import com.team13.patientclient.activities.fragments.ForumFragment;
import com.team13.patientclient.activities.fragments.HomeFragment;
import com.team13.patientclient.activities.fragments.ProfileEditFragment;
import com.team13.patientclient.activities.fragments.ProfileFragment;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.apis.AppointmentApi;
import com.team13.patientclient.repository.services.AppointmentService;
import com.team13.patientclient.repository.services.PatientService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        handleCurrentAppointment();
    }

    private void handleCurrentAppointment() {
        if (Store.get_instance().isHavingAnAppointment()){
            AppointmentService service = new AppointmentService();
            service.getAppointmentById(Store.get_instance().getCurrentAppointmentId(), new OnResponse<Appointment>() {
                @Override
                public void onRequestSuccess(Appointment response) {
                    Log.d("LONG", new Gson().toJson(response));
                    if (!curAppointmentIsValid(response)){
                        //Cur appointment is expired => Remove it
                        MyDialog.showOkDialog(MainActivity.this, "Your last appointment was canceled! Please be on time next time!", "Cancel alert", new Runnable() {
                            @Override
                            public void run() {
                            }
                        });

                        removeCurrentAppointment(response.getId());
                    }

                }

                @Override
                public void onRequestFailed(ErrorResponse response) {
                    Toast.makeText(MainActivity.this, "Verify user's last appointment failed! Attempting to try again.", Toast.LENGTH_LONG).show();
                    handleCurrentAppointment();
                }
            });
        }
    }

    private boolean curAppointmentIsValid(Appointment response) {
        String time1 = new SimpleDateFormat(Utils.DATETIME_PATTERN).format(new Date());
        String time2 = response.getTime() + " " + response.getDate();
        Log.d("COMPARE TIME", Utils.compareTimes(time1, time2) +"");
        //TODO
        if (Utils.compareTimes(time1, time2)) // If current time < appointment time => this appointment is still valid
            return true;

        //Valid only if this appointment is completed
        return response.getStatus().equals(Utils.STATUS_COMPLETED);
    }

    private void removeCurrentAppointment(String appointmentId) {
        PatientService patientService = new PatientService();
        patientService.removeCurrentAppointment(Store.get_instance().getPatientId(), appointmentId, new OnResponse<Void>() {
            @Override
            public void onRequestSuccess(Void response) {
                Store.get_instance().getUserAccount().getUserInfor().setCurrentAppointmentId(null);
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                handleCurrentAppointment();
            }
        });
    }

    private void updateStatusOfAppointment(String appointmentId){
        AppointmentService service = new AppointmentService();
        Map<String, String> data = new HashMap<>();
        data.put("status", Utils.STATUS_CANCELED);
        service.updateAppointment(appointmentId, data, new OnSuccessResponse<Void>() {
            @Override
            public void onSuccess(Void response) {

            }
        });
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
                loadFragment(new MedicalRecordFragment());
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