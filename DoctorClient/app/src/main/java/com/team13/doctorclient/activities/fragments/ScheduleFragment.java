package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;
    Calendar selectedDay;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
        horizontalCalendar = new HorizontalCalendar.Builder(view,R.id.calendarView)
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
                renderData(date);
            }

        });

        HospitalModel hospitalModel = Store.get_instance().getHospital();
        times = Utils.generateTimes(hospitalModel.getOpenTime(), hospitalModel.getCloseTime(), 30);
        times = Utils.getAvailableTime(times);
        renderData(true_day);
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
    void renderData(Calendar date){
        initTimeLine();
        loadFragment(R.id.timeline_display_container, new ProgressFragment());
        getAppointmentByDay(date);
        AppointmentService service = new AppointmentService();
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