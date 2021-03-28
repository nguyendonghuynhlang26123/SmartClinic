package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.team13.patientclient.R;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulePickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePickFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioGroup day1TimeGroup, day2TimeGroup;
    Timestamp timestamp;
    int selectedDay = 0;
    SchedulePickFragmentListener listener;
    final SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMM", Locale.US);
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.US);
    final String[] times = {"07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00",
                            "11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30",
                            "16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00",
                            "20:30","21:00","21:30","22:00"};
    public SchedulePickFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchedulePickFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchedulePickFragment newInstance(String param1, String param2) {
        SchedulePickFragment fragment = new SchedulePickFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_pick, container, false);
        timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp nextDayTimestamp = addDays(timestamp, 1);
        TextView day1 = view.findViewById(R.id.day1);
        TextView day2 = view.findViewById(R.id.day2);
        String[] days = new String[2];
        days[0] = dayFormat.format(timestamp);         // current day
        days[1] = dayFormat.format(nextDayTimestamp);  // next day
        day1.setText(days[0]);
        day2.setText(days[1]);
        day1TimeGroup = view.findViewById(R.id.day1_time_group);
        day2TimeGroup = view.findViewById(R.id.day2_time_group);
        renderTimeChoice(view);
        day1TimeGroup.check(day1TimeGroup.getChildAt(0).getId());
        day1TimeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            selectedDay = 0;
            if(day2TimeGroup.getCheckedRadioButtonId()!=-1)
                day2TimeGroup.clearCheck();
        });
        day2TimeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            selectedDay = 1;
            if(day1TimeGroup.getCheckedRadioButtonId()!=-1)
                day1TimeGroup.clearCheck();
        });
        view.findViewById(R.id.process_button).setOnClickListener(v->{
            RadioButton checkedButton;
            if(selectedDay==0){
                checkedButton = day1TimeGroup.findViewById(day1TimeGroup.getCheckedRadioButtonId());
            } else {
                checkedButton = day2TimeGroup.findViewById(day2TimeGroup.getCheckedRadioButtonId());
            }
            if (checkedButton==null)
                Toast.makeText(view.getContext(),"Please select a time", Toast.LENGTH_SHORT).show();
            else listener.gotoReasonPick(days[selectedDay] + " " + checkedButton.getText().toString());
        });
        return view;
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
        void gotoReasonPick(String time);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new Timestamp(cal.getTime().getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void renderTimeChoice(View view){
        String time = timeFormat.format(timestamp);
        int minute = Integer.parseInt(time.substring(3,5));
        int hour = Integer.parseInt(time.substring(0,2));
        LocalTime currentTime = LocalTime.of(hour, minute);
        int minute1, hour1;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Math.round(convertDpToPixel(4,view.getContext()));
        params.bottomMargin = Math.round(convertDpToPixel(4,view.getContext()));
        for (String s : times) {
            minute1 = Integer.parseInt(s.substring(3, 5));
            hour1 = Integer.parseInt(s.substring(0, 2));
            if (currentTime.isBefore(LocalTime.of(hour1, minute1))) {
                RadioButton timeButton = new RadioButton(view.getContext());
                timeButton.setText(s);
                timeButton.setLayoutParams(params);
                day1TimeGroup.addView(timeButton);
            }
            RadioButton timeButton = new RadioButton(view.getContext());
            timeButton.setText(s);
            timeButton.setLayoutParams(params);
            day2TimeGroup.addView(timeButton);
        }
    }
}