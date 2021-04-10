package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.team13.patientclient.NotificationHandler;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.AppointmentConfirmFragment;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.ReasonPickFragment;
import com.team13.patientclient.activities.fragments.SchedulePickFragment;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.AppointmentService;

import java.io.IOException;
import java.util.Map;

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
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        context = this;
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
    public String getServiceId() {
        return serviceId;
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
        String patientId = Store.get_instance().getPatientId();
        Appointment appointment = new Appointment(patientId, new ServicePack(serviceId), reason, date, time);
        AppointmentService service = new AppointmentService();

        Fragment fragment = new ProgressFragment();
        loadFragment(fragment);

        service.bookAnAppointment(appointment, Store.get_instance().getPatientId(), new OnResponse<Map<String, String>>() {
            @Override
            public void onRequestSuccess(Map<String, String> response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book successfully! Please visit and check in on time for diagnosis!");
                Store.get_instance().bookingAnAppointment(response.get("_id"));
                //setAlarmForNotification(response.get("_id"));
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i); 
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book failed!! " + response.getMessage());
                finish();
            }
        });
    }

    private void setAlarmForNotification(Appointment appointment) {
        Intent intent = new Intent(BookAppointmentActivity.this, AlarmReceiverActivity.class);
        intent.putExtra(Utils.BROADCAST_APPOINTMENT_ID,  appointment.getId());
        intent.putExtra(Utils.BROADCAST_PATIENT_ID,  Store.get_instance().getPatientId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BookAppointmentActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long curTime = System.currentTimeMillis();
        Log.d("LONG", "SET ALARM");
        alarmManager.set(AlarmManager.RTC_WAKEUP, curTime + 1000*10, pendingIntent);

    }
}