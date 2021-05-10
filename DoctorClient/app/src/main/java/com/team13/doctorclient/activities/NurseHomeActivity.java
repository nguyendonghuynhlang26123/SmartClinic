package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.team13.doctorclient.activities.fragments.NurseTimelineFragment;
import com.team13.doctorclient.activities.fragments.ProgressFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.models.ServicePack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class NurseHomeActivity extends AppCompatActivity {
    private HorizontalCalendar horizontalCalendar;
    Calendar selectedDay;
    ArrayList<ScheduleItem> data;
    ArrayList<Appointment> appointments = new ArrayList<>();
    String[] times = {
            "7:00","7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30", "12:00",
            "12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30",
            "18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"
    };
    HashMap<String, Integer> timeline = new HashMap<>(20);
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        selectedDay = Calendar.getInstance();
        TextView currentDay= findViewById(R.id.current_day);
        currentDay.setText(format.format(selectedDay.getTime()));
        selectedDay.add(Calendar.DATE,-1);
        horizontalCalendar = new HorizontalCalendar.Builder(this,R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5).defaultSelectedDate(selectedDay)
                .build();
        Calendar true_day = Calendar.getInstance();
        true_day.setTime(selectedDay.getTime());
        true_day.add(Calendar.DATE,1);
        renderData(true_day);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                date.add(Calendar.DATE,1);
                renderData(date);
            }

        });

    }

    void getAppointmentByDay(Calendar date){
        ServicePack servicePack = new ServicePack("Beauty Care","Face surgery",500000,"1");
        appointments.add(new Appointment("MN",servicePack,"cough",format.format(date.getTime()),"7:30","CANCEL"));
        appointments.add(new Appointment("MN",servicePack,"fever",format.format(date.getTime()),"9:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"headache",format.format(date.getTime()),"10:30","CANCEL"));
        appointments.add(new Appointment("MN",servicePack,"cough",format.format(date.getTime()),"13:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"fever",format.format(date.getTime()),"16:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"headache",format.format(date.getTime()),"18:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"",format.format(date.getTime()),"20:30","PENDING"));
    }
    ArrayList<ScheduleItem> getScheduleTimeline(){
        ArrayList<ScheduleItem> scheduleTimeline = new ArrayList<ScheduleItem>(20);
        for(String time: times){
            int appointmentIndex = timeline.get(time);
            if(appointmentIndex != -1){
                scheduleTimeline.add(new ScheduleItem(appointments.get(appointmentIndex),time));
            } else {
                scheduleTimeline.add(new ScheduleItem(null,time));
            }
        }
        return scheduleTimeline;
    }
    void initTimeLine(){
        for(String time: times){
            timeline.put(time, -1);
        }
    }

    void populateTimeline(){
        for(int i = 0; i < appointments.size(); ++i){
            timeline.put(appointments.get(i).getTime(),i);
        }
    }
    void renderData(Calendar date){
        initTimeLine();
        loadFragment(R.id.timeline_display_container, new ProgressFragment());
        getAppointmentByDay(date);
        populateTimeline();
        data = getScheduleTimeline();
        NurseTimelineFragment fragment = NurseTimelineFragment.newInstance(data);
        loadFragment(R.id.timeline_display_container, fragment);
    }
    private void loadFragment(int id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}