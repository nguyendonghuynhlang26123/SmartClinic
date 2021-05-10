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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<ScheduleItem> data;
    public ScheduleTimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TimelineDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleTimelineFragment newInstance(ArrayList<ScheduleItem> param1) {
        ScheduleTimelineFragment fragment = new ScheduleTimelineFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (ArrayList<ScheduleItem>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_timeline, container, false);
        RecyclerView timeline= view.findViewById(R.id.doctor_timeline);
        ScheduleTimelineAdapter adapter = new ScheduleTimelineAdapter(view.getContext(),data);
        timeline.setAdapter(adapter);
        timeline.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

}