package com.team13.patientclient.activities.fragments;

import android.content.Context;
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
    final SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMM", Locale.US);
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


    private View renderTimeChoice(View view, String day){
        Log.d("LONG_DEBUG", "START_RENDER " + day);
        View card = getLayoutInflater().inflate(R.layout.time_picking_item,null, false);

        RadioGroup radioGroup = card.findViewById(R.id.time_pick_group);
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> shifts = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );

        ((TextView) card.findViewById(R.id.time_pick_day)).setText(day);

        //Events
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectingBtn = group.findViewById(checkedId);
            handleRadioBtnClicked(selectingBtn);
        });

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Math.round(convertDpToPixel(4,getContext()));
        params.bottomMargin = Math.round(convertDpToPixel(4,getContext()));

        for (int i = 0; i < shifts.size(); i++) {
            String s = shifts.get(i);
            RadioButton timeButton = new RadioButton(getContext());
            timeButton.setText(s);
            timeButton.setTag(day);
            timeButton.setLayoutParams(params);

            radioGroup.addView(timeButton);
        }

        return card;
    }

    boolean compareBtn(RadioButton btnA, RadioButton btnB){
        return btnA.getTag().equals(btnB.getTag()) && btnA.getText().equals(btnB.getText());
    }

    void handleRadioBtnClicked (RadioButton selectingBtn) {
        if (activeBtn != null) {
            if (compareBtn(selectingBtn, activeBtn)) return;
            activeBtn.setChecked(false);
            Log.d("LONG", "BEFORE: " + activeBtn.getTag() + " - " + activeBtn.getText());
        }
        activeBtn = selectingBtn;
        activeBtn.setChecked(true);
        Log.d("LONG", "AFTER: " + activeBtn.getTag() + " - " + activeBtn.getText());
    }
}