package com.team13.doctorclient.activities.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment {
    Calendar selectedDay;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    ArrayList<Appointment> appointments = new ArrayList<>();
    ArrayList<ScheduleItem> data;
    ArrayList<String> times;
    HashMap<String, Integer> timeline = new HashMap<>(20);

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        selectedDay = Calendar.getInstance();
        TextView currentDay= view.findViewById(R.id.current_day);
        currentDay.setText(format.format(selectedDay.getTime()));
        selectedDay.add(Calendar.DATE,-1);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5).defaultSelectedDate(selectedDay)
                .build();
        Calendar true_day = Calendar.getInstance();
        true_day.setTime(selectedDay.getTime());
        true_day.add(Calendar.DATE,1);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                date.add(Calendar.DATE,1);
                Log.d("FIND",format.format(date.getTime()));
                if(Utils.isToday(date)) {
                    renderData(format.format(date.getTime()), Utils.EDIT_MODE);
//                    renderData("12/05/2021", Utils.EDIT_MODE);
                }
                else renderData(format.format(date.getTime()), Utils.VIEW_MODE);
            }

        });

        String dateString = format.format(true_day.getTime());
        HospitalModel hospitalModel = Store.get_instance().getHospital();
        times = Utils.generateTimes(hospitalModel.getOpenTime(), hospitalModel.getCloseTime(), 30);
        //times = Utils.getAvailableTime(times);

        //renderData(dateString, Utils.EDIT_MODE);
        renderData("12/05/2021", Utils.EDIT_MODE);
        return view;
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

    void renderData(String date, int mode){
        initTimeLine();
        loadFragment(R.id.timeline_display_container, new ProgressFragment());

        String[] queryStatus = new String[1];
        if (mode == Utils.EDIT_MODE) queryStatus[0] = (Utils.STATUS_PROCESSING);
        else queryStatus[0] = (Utils.STATUS_PENDING);

        AppointmentService service = new AppointmentService();
        service.getAppointmentByDate(date,Store.get_instance().getId(),queryStatus, new OnResponse<Appointment[]>() {
            @Override
            public void onRequestSuccess(Appointment[] response) {
                Log.d("FIND",response.length+"");
                appointments = new ArrayList<>(Arrays.asList(response));
                populateTimeline();
                data = getScheduleTimeline();
                ScheduleTimelineFragment fragment = ScheduleTimelineFragment.newInstance(data);
                loadFragment(R.id.timeline_display_container, fragment);
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                Snackbar snackbar = Snackbar
                        .make(getView(), "Cannot access to Server! Please try again.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try again", view -> renderData(date, mode));
                snackbar.show();
            }
        });


    }
    private void loadFragment(int id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}