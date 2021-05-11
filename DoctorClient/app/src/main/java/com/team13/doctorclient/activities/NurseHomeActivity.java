package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.fragments.NurseTimelineFragment;
import com.team13.doctorclient.activities.fragments.ProgressFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class NurseHomeActivity extends AppCompatActivity {
    ArrayList<ScheduleItem> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        TextView currentDay= findViewById(R.id.current_day);
        currentDay.setText(Utils.getCurrentDateString());

        renderData(Utils.getCurrentDateString());

    }

    void getAppointmentByDay(String date){
    }

    ArrayList<ScheduleItem> getScheduleTimeline(ArrayList<String> times){
        ArrayList<ScheduleItem> scheduleTimeline = new ArrayList<ScheduleItem>(20);
        for(String time: times){
            int appointmentIndex = times.indexOf(time);
            if(appointmentIndex != -1){
                //scheduleTimeline.add(new ScheduleItem(appointments.get(appointmentIndex),time));
            } else {
                scheduleTimeline.add(new ScheduleItem(null,time));
            }
        }
        return scheduleTimeline;
    }
    void renderData(String date){
        //Get list of possible time
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> timeLines = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );

        //Render the timeline view
        AppointmentService service = new AppointmentService();
        service.getAppointmentByDate(date, new OnSuccessResponse<Appointment[]>() {
            @Override
            public void onSuccess(Appointment[] response) {
                Log.d("LONG", new Gson().toJson(response));
            }
        });

        //Get And render appointments
    }
}