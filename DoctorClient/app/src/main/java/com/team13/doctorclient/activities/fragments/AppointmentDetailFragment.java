package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;

public class AppointmentDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private Appointment appointment;

    public AppointmentDetailFragment() {
        // Required empty public constructor
    }

    public static AppointmentDetailFragment newInstance(Appointment appointment) {
        AppointmentDetailFragment fragment = new AppointmentDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appointment = (Appointment)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_detail, container, false);
        TextView patientTime = view.findViewById(R.id.patient_time);
        patientTime.setText(String.format("%s %s", appointment.getTime(), appointment.getDate()));
        TextView patientTreatment = view.findViewById(R.id.patient_treatment);
        patientTreatment.setText(appointment.getService().getName());
        TextView price = view.findViewById(R.id.appointment_price);
        price.setText(String.valueOf(appointment.getService().getPrice()));
        return view;
    }
}