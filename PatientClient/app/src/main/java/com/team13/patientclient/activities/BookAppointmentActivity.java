package com.team13.patientclient.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.NotificationHandler;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.AppointmentConfirmFragment;
import com.team13.patientclient.activities.fragments.ChooseDoctorFragment;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.ReasonPickFragment;
import com.team13.patientclient.activities.fragments.SchedulePickFragment;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.AppointmentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class BookAppointmentActivity extends AppCompatActivity implements
        SchedulePickFragment.SchedulePickFragmentListener,
        ReasonPickFragment.ReasonPickFragmentListener,
        AppointmentConfirmFragment.AppointmentConfirmListener,
        ChooseDoctorFragment.Listener {
    String time;
    String date;
    String doctorId;
    String serviceId;
    String serviceName;
    String reason;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        context = this;
        loadFragment(new ChooseDoctorFragment());

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
    public String getDoctorId() {
        return this.doctorId;
    }

    @Override
    public void goToSchedulePick(String doctorId) {
        this.doctorId = doctorId;
        loadFragment(new SchedulePickFragment());
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
        String patientId = Store.get_instance().getPatientId(); ;
        AppointmentService service = new AppointmentService();

        loadFragment(new ProgressFragment());

        service.bookAnAppointment(patientId, doctorId, serviceId, time, date, reason, new OnResponse<Map<String, String>>() {
            @Override
            public void onRequestSuccess(Map<String, String> response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book successfully! Please visit and check in on time for diagnosis!");
                Store.get_instance().bookingAnAppointment(response.get("_id"));
                //setAlarmForNotification(response.get("_id"));
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.CONTENT_URI);

                if (ContextCompat.checkSelfPermission(BookAppointmentActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(BookAppointmentActivity.this, new String[] { Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);
                } else addEventToCalendar("Meeting with doctor", "HELLO", Store.get_instance().getHospital().getAddress());

            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                NotificationHandler.sendNotification(BookAppointmentActivity.this, "Smart clinic", "Book failed!! " + response.getMessage());
                finish();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            addEventToCalendar("Meeting with doctor", "HELLO", Store.get_instance().getHospital().getAddress());
        }
    }

    public void addEventToCalendar(String EventTitle, String EventDescription, String EventLocation) {
        Date dateStart = null;
        try {
            dateStart = new SimpleDateFormat(Utils.DATETIME_PATTERN).parse(time + " " + date);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        if (dateStart == null) return;
        Toast.makeText(this, "Adding Event To Your Calendar...", Toast.LENGTH_SHORT).show();
        ContentValues event = new ContentValues();
        Calendar startcalendar = Calendar.getInstance();
        Calendar endcalendar = Calendar.getInstance();
        startcalendar.setTime(dateStart);
        endcalendar.setTime(dateStart);
        endcalendar.add(Calendar.MINUTE, 30);

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        //intent.putExtra(CalendarContract.Events.CALENDAR_ID, 1);
        intent.putExtra(CalendarContract.Events.TITLE, EventTitle);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, EventDescription);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, EventLocation);
        intent.putExtra(CalendarContract.Events.DTSTART, startcalendar.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.DTEND, endcalendar.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.HAS_ALARM, true);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT-07:00");

//        intent.setData(CalendarContract.Reminders.CONTENT_URI);
//        //intent.putExtra(CalendarContract.Reminders.EVENT_ID, eventId);
//        intent.putExtra(CalendarContract.Reminders.MINUTES, 10);
//        intent.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(BookAppointmentActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(BookAppointmentActivity.this , "Event Added To Your Calendar!", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(context, MainActivity.class);
//        context.startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}