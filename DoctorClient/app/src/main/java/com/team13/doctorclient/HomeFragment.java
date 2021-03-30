package com.team13.doctorclient;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.doctorclient.adapters.AppointmentScheduleAdapter;
import com.team13.doctorclient.models.Appointment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView appointmentScheduleList;
    HomeFragmentListener listener;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        appointmentScheduleList= view.findViewById(R.id.appointment_schedule_list);
        AppointmentScheduleAdapter appointmentScheduleAdapter= new AppointmentScheduleAdapter(view.getContext(),getSchedule());
        appointmentScheduleList.setAdapter(appointmentScheduleAdapter);
        appointmentScheduleList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }
    public interface HomeFragmentListener{

    }


    ArrayList<Appointment> getSchedule(){
        ArrayList<Appointment> appointments= new ArrayList<Appointment>(5);
        for(int i=0;i<7;++i){
            appointments.add(new Appointment("P001","D001","27th Mar","1:30pm - 2:30pm"));
        }
        return appointments;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}