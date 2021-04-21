package com.team13.doctorclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.models.ServicePack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    ArrayList<Appointment> appointments = new ArrayList<>();
    ArrayList<ScheduleItem> data;
    String[] times = {
            "7:00","7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30", "12:00",
            "12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30",
            "18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"
    };
    HashMap<String, Integer> timeline = new HashMap<>(20);
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

    void getAppointmentByDay(Calendar date){
        ServicePack servicePack = new ServicePack("Beauty Care","Face surgery",500000,"1");
        appointments.add(new Appointment("MN",servicePack,"cough","20/04/2021","7:30","CANCEL"));
        appointments.add(new Appointment("MN",servicePack,"fever","20/04/2021","9:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"headache","20/04/2021","10:30","CANCEL"));
        appointments.add(new Appointment("MN",servicePack,"cough","20/04/2021","13:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"fever","20/04/2021","16:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"headache","20/04/2021","18:30","PROCESSING"));
        appointments.add(new Appointment("MN",servicePack,"","20/04/2021","20:30","PENDING"));

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
    void renderData(Calendar date){
        initTimeLine();
        loadFragment(R.id.timeline_display_container, new ProgressFragment());
        getAppointmentByDay(date);
        populateTimeline();
        data = getScheduleTimeline();
        ScheduleTimelineFragment fragment = ScheduleTimelineFragment.newInstance(data);
        loadFragment(R.id.timeline_display_container, fragment);
    }
    private void loadFragment(int id, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}