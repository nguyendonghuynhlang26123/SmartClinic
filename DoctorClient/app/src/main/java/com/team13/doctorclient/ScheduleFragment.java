package com.team13.doctorclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.adapters.DoctorTimelineAdapter;
import com.team13.doctorclient.models.DoctorTimeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HorizontalCalendar horizontalCalendar;
    Calendar selectedDay;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<MyAppointment> appointments = new ArrayList<>();
    ArrayList<DoctorTimeline> data;
    String[] times = {"7:00","7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"};
    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getAppointments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
//        endDate.add(Calendar.DAY_OF_MONTH, -1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        selectedDay = Calendar.getInstance();
        selectedDay.add(Calendar.DATE,-1);
        horizontalCalendar = new HorizontalCalendar.Builder(view,R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5).defaultSelectedDate(selectedDay)
                .build();
        renderData(selectedDay);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                renderData(date);
            }

        });
        return view;
    }

    private class MyAppointment{
        String patientName;
        String treatment;
        String time;
        public MyAppointment( String patientName, String treatment, String time){
            this.patientName = patientName;
            this.treatment = treatment;
            this.time= time;
        }
    }
    void getAppointments(){
        appointments.add(new MyAppointment("MN","Beauty","7:30"));
        appointments.add(new MyAppointment("MN","Beauty","9:30"));
        appointments.add(new MyAppointment("MN","Beauty","10:30"));
        appointments.add(new MyAppointment("MN","Beauty","13:30"));
        appointments.add(new MyAppointment("MN","Beauty","16:30"));
        appointments.add(new MyAppointment("MN","Beauty","18:30"));
        appointments.add(new MyAppointment("MN","Beauty","20:30"));

    }
    int isSeized(String time){
        for(MyAppointment appointment: appointments){
            if(time.equals(appointment.time)) return appointments.indexOf(appointment);
        }
        return -1;
    }
    ArrayList<DoctorTimeline> getTimeline(Calendar date){
        ArrayList<DoctorTimeline> doctorTimelineArrayList = new ArrayList<DoctorTimeline>(10);
        for(String s: times){
            int a = isSeized(s);
            Date day = date.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day);
            calendar.add(Calendar.DATE,1);
            if(a!=-1){
                MyAppointment appointment = appointments.get(a);

                doctorTimelineArrayList.add(new DoctorTimeline(true,appointment.patientName,appointment.treatment,format.format(calendar.getTime()),s));
            } else {
                doctorTimelineArrayList.add(new DoctorTimeline(false,null,null, format.format(calendar.getTime()), s));
            }
        }
        return doctorTimelineArrayList;
    }
    void renderData(Calendar date){
        loadFragment(R.id.timeline_display_container, new ProgressFragment());
        data = getTimeline(date);
        TimelineDisplayFragment fragment = TimelineDisplayFragment.newInstance(data);
        loadFragment(R.id.timeline_display_container, fragment);
    }
    private void loadFragment(int Id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(Id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}