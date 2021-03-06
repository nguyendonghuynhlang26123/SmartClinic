package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.ScheduleTimelineAdapter;
import com.team13.doctorclient.models.ScheduleItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleTimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleTimelineFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int mode;

    private ArrayList<ScheduleItem> data;
    public ScheduleTimelineFragment() {
        // Required empty public constructor
    }

    public static ScheduleTimelineFragment newInstance(ArrayList<ScheduleItem> param1, int param2) {
        ScheduleTimelineFragment fragment = new ScheduleTimelineFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (ArrayList<ScheduleItem>) getArguments().getSerializable(ARG_PARAM1);
            mode = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_timeline, container, false);
        RecyclerView timeline= view.findViewById(R.id.doctor_timeline);
        ScheduleTimelineAdapter adapter = new ScheduleTimelineAdapter(view.getContext(),data, mode);
        timeline.setAdapter(adapter);
        timeline.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

}