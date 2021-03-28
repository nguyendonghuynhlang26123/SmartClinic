package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team13.patientclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentConfirmFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AppointmentConfirmListener listener;
    public AppointmentConfirmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentConfirmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentConfirmFragment newInstance(String param1, String param2) {
        AppointmentConfirmFragment fragment = new AppointmentConfirmFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment_confirm, container, false);
        TextView selectedTime = view.findViewById(R.id.selected_time);
        selectedTime.setText(listener.getSelectedTime());
        TextView selectedService = view.findViewById(R.id.selected_service);
        selectedService.setText(listener.getSelectedService());
        TextView reason = view.findViewById(R.id.selected_reason);
        reason.setText(listener.getReason());
        view.findViewById(R.id.process_button).setOnClickListener(v->listener.handleConfirm());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppointmentConfirmListener) {
            listener = (AppointmentConfirmListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AppointmentConfirmListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface AppointmentConfirmListener{
        String getSelectedTime();
        String getSelectedService();
        String getReason();
        void handleConfirm();
    }
}