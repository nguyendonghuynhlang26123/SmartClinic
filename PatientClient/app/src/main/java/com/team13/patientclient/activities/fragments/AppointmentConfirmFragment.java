package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.models.HospitalModel;

import org.jetbrains.annotations.NotNull;


public class AppointmentConfirmFragment extends Fragment {

    AppointmentConfirmListener listener;
    public AppointmentConfirmFragment() {
        // Required empty public constructor
    }

    public static AppointmentConfirmFragment newInstance() {
        AppointmentConfirmFragment fragment = new AppointmentConfirmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_confirm, container, false);
        TextView selectedTime = view.findViewById(R.id.selected_time);
        TextView selectedService = view.findViewById(R.id.selected_service);
        TextView reason = view.findViewById(R.id.selected_reason);

        selectedService.setText(listener.getSelectedService());
        String text = listener.getSelectedTime() + ", " + listener.getSelectedDate();
        selectedTime.setText(text);
        reason.setText(listener.getReason());
        view.findViewById(R.id.process_button).setOnClickListener(v->listener.handleConfirm());


        HospitalModel hospital = Store.get_instance().getHospital();

        if (!hospital.getImgUrl().isEmpty()) {
            Picasso.get().load(hospital.getImgUrl()).into((ImageView) view.findViewById(R.id.hospital_thumbnail));
        }

        Log.d("LONG", new Gson().toJson(hospital));
        ((TextView) view.findViewById(R.id.hospital_name)).setText(hospital.getName());
        ((TextView) view.findViewById(R.id.hospital_address)).setText(hospital.getAddress());
        String workingHours = hospital.getDayOfWorks() + ", " + hospital.getOpenTime() + " - " + hospital.getCloseTime();
        ((TextView) view.findViewById(R.id.hospital_working_time)).setText(workingHours);

        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
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
        String getSelectedDate();
        String getSelectedService();
        String getReason();
        void handleConfirm();
    }
}