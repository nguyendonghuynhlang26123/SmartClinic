package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.team13.patientclient.NotificationHandler;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.fragments.AppointmentConfirmFragment;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.ReasonPickFragment;
import com.team13.patientclient.activities.fragments.SchedulePickFragment;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.AppointmentService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentActivity extends AppCompatActivity implements
        SchedulePickFragment.SchedulePickFragmentListener,
        ReasonPickFragment.ReasonPickFragmentListener,
        AppointmentConfirmFragment.AppointmentConfirmListener {
    String time;
    String date;
    String serviceId;
    String serviceName;
    String reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        loadFragment(new SchedulePickFragment());
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        Intent i = getIntent();
        serviceName = i.getStringExtra("service_name");
        serviceId = i.getStringExtra("service_id");
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void gotoReasonPick(String time, String date) {
        this.time = time;
        this.date = date;
        loadFragment(new ReasonPickFragment());
    }

    @Override
    public void gotoAppointmentConfirm(String reason) {
        this.reason = reason;
        loadFragment(new AppointmentConfirmFragment());
    }

    @Override
    public String getSelectedTime() {
        return this.time;
    }

    @Override
    public String getSelectedDate() {
        return this.date;
    }

    @Override
    public String getSelectedService() {
        return this.serviceName;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public void handleConfirm() {
        // Handle processing when click appointment confirm button
        //*** TODO ***//
        String patientId = Store.get_instance().getUserAccount().getUserInfor().getId();
        Appointment appointment = new Appointment(patientId, serviceId, reason, date, time);
        AppointmentService service = new AppointmentService();

        Fragment fragment = new ProgressFragment();
        loadFragment(fragment);

        service.bookAnAppointment(appointment, new OnResponse<Appointment>() {
            @Override
            public void onRequestSuccess(Appointment response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book successfully! Please visit and check in on time for diagnosis!");
                finish();
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book failed!! " + response.getMessage());
                finish();
            }
        });
    }
}