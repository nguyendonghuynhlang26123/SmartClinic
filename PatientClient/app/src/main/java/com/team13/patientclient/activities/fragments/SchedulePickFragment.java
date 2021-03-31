package com.team13.patientclient.activities.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
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

import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.models.HospitalModel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulePickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePickFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Timestamp timestamp;
    SchedulePickFragmentListener listener;
    RadioButton activeBtn;
    final SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM", Locale.US);
    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

    public SchedulePickFragment() {
        // Required empty public constructor
    }

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

        View day1 = renderTimeChoice(view, days[0]);
        View day2 = renderTimeChoice(view, days[1]);

        LinearLayout layout = view.findViewById(R.id.schedule_groups);
        layout.addView(day1);
        layout.addView(day2);

        ((Button) view.findViewById(R.id.process_button)).setOnClickListener(l->{
            if (activeBtn == null) {
                Toast.makeText(getContext(), "Please select a day!", Toast.LENGTH_SHORT).show();
                return;
            }

            listener.gotoReasonPick(activeBtn.getText().toString(), activeBtn.getTag().toString());
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
        void gotoReasonPick(String time, String date);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    @SuppressLint("NewApi")
    private View renderTimeChoice(View view, String day){
        View card = getLayoutInflater().inflate(R.layout.time_picking_item,null, false);

        RadioGroup radioGroup = card.findViewById(R.id.time_pick_group);
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> shifts = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );
        //Render only available time
        if (day.equals(dayFormat.format(timestamp))){
            shifts = getAvailableTime(shifts);
        }

        ((TextView) card.findViewById(R.id.time_pick_day)).setText(day);

        if (shifts.size() == 0){
            TextView notification = new TextView(getContext());
            notification.setText("Sorry! No more appointment is available today!");
            notification.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            radioGroup.addView(notification);
        }
        Log.d("LONG", "" + shifts);

        //Events
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectingBtn = group.findViewById(checkedId);
            handleRadioBtnClicked(selectingBtn);
        });

        for (int i = 0; i < shifts.size(); i++) {
            String s = shifts.get(i);
            RadioButton timeButton = new RadioButton(getContext());
            timeButton.setText(s);
            timeButton.setTag(day);
            stylingRadioBtn(timeButton);

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
                        new int [] {android.R.attr.state_checked},
                        new int [] {-android.R.attr.state_checked},
                        new int [] {-android.R.attr.state_enabled},
                },
                new int [] {
                        Color.WHITE,
                        Color.parseColor("#FFBB86FC"),
                        Color.parseColor("#374151"),
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
}