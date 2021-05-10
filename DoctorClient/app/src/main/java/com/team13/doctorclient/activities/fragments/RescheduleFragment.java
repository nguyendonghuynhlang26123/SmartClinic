package com.team13.doctorclient.activities.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ServicePack;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RescheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RescheduleFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String rescheduleTime;

    Button cancelBtn,rescheduleBtn;
    ImageView pickDay,pickTime;
    TextView dayPicked,timePicked;
    Calendar myCalendar;
    DatePickerDialog dialog;
    String date,time;
    RescheduleListener listener;
    public RescheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RescheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RescheduleFragment newInstance(String param1) {
        RescheduleFragment fragment = new RescheduleFragment();
        Log.d("DANG","creating reschedule");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rescheduleTime = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reschedule, container, false);

        myCalendar= Calendar.getInstance();
        dayPicked=view.findViewById(R.id.date_picked);
        date=rescheduleTime.split(" ")[0];
        dayPicked.setText(date);
        timePicked=view.findViewById(R.id.time_picked);
        time=rescheduleTime.split(" ")[1];
        timePicked.setText(time);
        pickDay=view.findViewById(R.id.pick_date);
        pickDay.setOnClickListener(v -> {
            DatePickerDialog dialog=new DatePickerDialog(view.getContext(), (view1, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                date=""+dayOfMonth+"/"+(month+1)+"/"+year;
                dayPicked.setText(date);
            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
        pickTime= view.findViewById(R.id.pick_time);
        pickTime.setOnClickListener(v -> {
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Pick a time")
                    .build();
            picker.show(getFragmentManager(),null);

            picker.addOnPositiveButtonClickListener(v1 -> {
                time= String.valueOf(picker.getHour());
                if(picker.getMinute()<=15){
                    time+=":00";
                    timePicked.setText(time);

                }
                else if(picker.getMinute()>15&&picker.getMinute()<45){
                    time+=":30";
                    timePicked.setText(time);
//                        Log.w("Goto","1");
                }
                else if(picker.getMinute()>=45) {
                    time= String.valueOf(picker.getHour()+1)+":00";
                    timePicked.setText(time);
//                        Log.w("Goto","1");
                }
            });


        });
        cancelBtn=view.findViewById(R.id.cancel_button);

        rescheduleBtn=view.findViewById(R.id.reschedule_button);
        rescheduleBtn.setOnClickListener(v -> {
            Log.w("Date",date);
            ServicePack servicePack = new ServicePack("Beauty Care", "no", 500000,"1");
            Appointment appointment = new Appointment("MN",servicePack,"reschedule",date,time,"PENDING");
            listener.setReschedule(appointment);
        });
        return view;
    }
    public interface RescheduleListener{
        void setReschedule(Appointment appointment);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RescheduleListener) {
            listener= (RescheduleListener) context;
        }
        else {
//            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener= null;
    }
}