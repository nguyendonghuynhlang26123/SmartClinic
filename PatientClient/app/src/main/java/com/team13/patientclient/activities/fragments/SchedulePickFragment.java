package com.team13.patientclient.activities.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.HospitalModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.AppointmentService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulePickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePickFragment extends Fragment {

    AppointmentService appointmentService;
    Timestamp timestamp;
    SchedulePickFragmentListener listener;
    RadioButton activeBtn;
    final SimpleDateFormat dayFormat = new SimpleDateFormat(Utils.DATE_PATTERN, Locale.US);
    final SimpleDateFormat timeFormat = new SimpleDateFormat(Utils.TIME_PATTERN , Locale.US);

    public SchedulePickFragment() {
        // Required empty public constructor
    }

    public static SchedulePickFragment newInstance(String param1, String param2) {
        SchedulePickFragment fragment = new SchedulePickFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appointmentService = new AppointmentService();

        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_pick, container, false);
        timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp nextDayTimestamp = Utils.addDays(timestamp, 1);
        String[] days = new String[2];
        days[0] = dayFormat.format(timestamp);         // current day
        days[1] = dayFormat.format(nextDayTimestamp);  // next day
        LinearLayout layout = view.findViewById(R.id.schedule_groups);

        if (Store.get_instance().isHavingAnAppointment()){
            layout.addView(denyBookingNotification());
            return view;
        }

        addSchedule(layout, days[0]);
        addSchedule(layout, days[1]);

        view.findViewById(R.id.process_button).setOnClickListener(l->{
            if (activeBtn == null) {
                Toast.makeText(getContext(), "Please select a day!", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] tag = activeBtn.getTag().toString().split(";");
            listener.gotoReasonPick(tag[0], tag[1]);
        });
        return view;
    }

    private void addSchedule(LinearLayout layout, String day) {
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> shifts = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );
        //Render only available time
        if (day.equals(dayFormat.format(timestamp))){
            shifts = getAvailableTime(shifts);
        }

        if (shifts.size() == 0){
            View day1 = renderTimeChoice(day, shifts, new HashSet<>());
            layout.addView(day1);
        }else{
            ArrayList<String> finalShifts = shifts;
            appointmentService.getAppointmentByDate(day, listener.getServiceId(), new OnSuccessResponse<Appointment[]>() {
                @Override
                public void onSuccess(Appointment[] appointments) {
                    Set<String> existedAppointment = new HashSet<>();
                    for (Appointment appointment : appointments){
                        existedAppointment.add(appointment.getTime());
                    }

                    View day1 = renderTimeChoice(day, finalShifts, existedAppointment);
                    layout.addView(day1);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SchedulePickFragmentListener) {
            listener = (SchedulePickFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SchedulePickFragmentListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface SchedulePickFragmentListener{
        void gotoReasonPick(String time, String date);
        String getServiceId();
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @SuppressLint("NewApi")
    private View denyBookingNotification(){
        View card = getLayoutInflater().inflate(R.layout.time_picking_item,null, false);
        ((TextView) card.findViewById(R.id.time_pick_day)).setText("⚠ You already has an appointment booked!");
        RadioGroup radioGroup = card.findViewById(R.id.time_pick_group);
        TextView notification = new TextView(getContext());
        notification.setText("You can cancel your appointment in the appointment view");
        notification.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        radioGroup.addView(notification);

        return card;
    }

    @SuppressLint("NewApi")
    private View renderTimeChoice(String day, ArrayList<String> shifts, Set<String> thatDayAppointments){
        View card = getLayoutInflater().inflate(R.layout.time_picking_item,null, false);

        ((TextView) card.findViewById(R.id.time_pick_day)).setText(day);
        RadioGroup radioGroup = card.findViewById(R.id.time_pick_group);

        if (shifts.size() == 0){
            TextView notification = new TextView(getContext());
            notification.setText("Sorry! No more appointment is available today!");
            notification.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            radioGroup.addView(notification);
        }

        //Events
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectingBtn = group.findViewById(checkedId);
            handleRadioBtnClicked(selectingBtn);
        });

        for (int i = 0; i < shifts.size(); i++) {
            String time = shifts.get(i);
            RadioButton timeButton = new RadioButton(getContext());
            timeButton.setText(time);
            timeButton.setTag(time + ";" + day);
            stylingRadioBtn(timeButton);
            if (checkAppointmentIsExisting(time, thatDayAppointments)){
                timeButton.setEnabled(false);
            }
            radioGroup.addView(timeButton);
        }

        return card;
    }

    @SuppressLint("NewApi")
    void stylingRadioBtn(RadioButton btn){
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams( RadioGroup.LayoutParams.MATCH_PARENT ,RadioGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Math.round(convertDpToPixel(4,getContext()));
        params.bottomMargin = Math.round(convertDpToPixel(4,getContext()));

        btn.setLayoutParams(params);
        btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btn.setTextSize(16);
        btn.setButtonDrawable(R.color.transparent);
        btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radio_btn_bg_selector));

        int pixel = (int) convertDpToPixel(10, getContext());
        btn.setPadding(0,pixel,0,pixel);

        btn.setTextColor(new ColorStateList(
                new int [] [] {
                        new int [] {android.R.attr.state_checked, android.R.attr.state_enabled},
                        new int [] {-android.R.attr.state_checked, android.R.attr.state_enabled},
                        new int [] {-android.R.attr.state_enabled, android.R.attr.state_checked},
                        new int [] {-android.R.attr.state_enabled, -android.R.attr.state_checked},
                },
                new int [] {
                        Color.WHITE,
                        Color.parseColor("#6d4c41"),
                        Color.parseColor("#cccccc"),
                        Color.parseColor("#cccccc"),
                }
        ));
    }

    @SuppressLint("NewApi")
    ArrayList<String> getAvailableTime(ArrayList<String> curShifts) {
        String curTime = timeFormat.format(timestamp);
        int curMinute = Integer.parseInt(curTime.substring(3,5));
        int curHour = Integer.parseInt(curTime.substring(0,2));
        LocalTime currentTime = LocalTime.of(curHour,curMinute );

        for (int i = 0; i < curShifts.size(); i++) {
            String time = curShifts.get(i);
            int min = Integer.parseInt(time.substring(3,5));
            int hour = Integer.parseInt(time.substring(0,2));

            if (currentTime.isBefore(LocalTime.of(hour,min))) {
                Log.d("LONG", "STOP AT" + i + " - " + time + " at " + curTime);
                return new ArrayList<>(curShifts.subList(i, curShifts.size()));
            }
        }
        return new ArrayList<>();
    }

    boolean compareBtn(RadioButton btnA, RadioButton btnB){
        return btnA.getTag().equals(btnB.getTag()) && btnA.getText().equals(btnB.getText());
    }

    void handleRadioBtnClicked (RadioButton selectingBtn) {
        if (activeBtn != null) {
            if (compareBtn(selectingBtn, activeBtn)) return;
            activeBtn.setChecked(false);
        }
        activeBtn = selectingBtn;
        activeBtn.setChecked(true);
    }

    boolean checkAppointmentIsExisting(String time, Set<String> existing) {
        return existing.contains(time);
    }
}