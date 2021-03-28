package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.AppointmentItemAdapter;
import com.team13.patientclient.models.Appointment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView appointmentList;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        appointmentList = view.findViewById(R.id.appointment_list);
        AppointmentItemAdapter appointmentItemAdapter = new AppointmentItemAdapter(view.getContext(), getAppointments());
        appointmentList.setAdapter(appointmentItemAdapter);
        appointmentList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        view.findViewById(R.id.add_appointment_button).setOnClickListener(v->{
            Intent i = new Intent(view.getContext(), ServiceActivity.class);
            startActivity(i);
        });
        return view;
    }

    ArrayList<Appointment> getAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>(3);
        for(int i=0;i<3;++i){
            appointments.add(new Appointment("P01","D01","12 February 2021", "6:00AM - 9:00AM"));
        }
        return appointments;
    }
}