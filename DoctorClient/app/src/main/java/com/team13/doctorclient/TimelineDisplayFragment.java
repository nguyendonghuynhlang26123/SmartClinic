package com.team13.doctorclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.doctorclient.adapters.DoctorTimelineAdapter;
import com.team13.doctorclient.models.DoctorTimeline;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<DoctorTimeline> data;
    public TimelineDisplayFragment() {
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
    public static TimelineDisplayFragment newInstance(ArrayList<DoctorTimeline> param1) {
        TimelineDisplayFragment fragment = new TimelineDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (ArrayList<DoctorTimeline>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline_display, container, false);
        RecyclerView timeline= view.findViewById(R.id.doctor_timeline);
        DoctorTimelineAdapter doctorTimelineAdapter = new DoctorTimelineAdapter(view.getContext(),data);
        timeline.setAdapter(doctorTimelineAdapter);
        timeline.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

}