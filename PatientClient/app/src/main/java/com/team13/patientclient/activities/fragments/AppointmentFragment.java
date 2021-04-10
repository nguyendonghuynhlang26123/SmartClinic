package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.TreatmentAdapter;
import com.team13.patientclient.models.DrugDetail;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.models.Prescription;
import com.team13.patientclient.models.Treatment;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.PatientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        TextView notify = view.findViewById(R.id.appointment_notify);
        RecyclerView treatmentList = view.findViewById(R.id.treatment_list);
        TreatmentAdapter adapter = new TreatmentAdapter(view.getContext(), new TreatmentAdapter.TreatmentItemListener() {
            @Override
            public void onItemClick(Prescription prescription) {
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(prescription);
                fragment.show(getFragmentManager(),fragment.getTag());
            }

            @Override
            public void onHasAppointment() {
                view.findViewById(R.id.add_appointment_button).setVisibility(View.INVISIBLE);
                notify.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAppointmentRemove(String appointmentId) {
                notify.setVisibility(View.GONE);
                PatientService service = new PatientService();
                service.cancelAppointment(Store.get_instance().getPatientId(), appointmentId, new OnSuccessResponse<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Toast.makeText(getContext(), "Appointment Canceled!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        treatmentList.setAdapter(adapter);
        treatmentList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        view.findViewById(R.id.add_appointment_button).setOnClickListener(v->{
            Intent i = new Intent(view.getContext(), ServiceActivity.class);
            startActivity(i);
        });

        callApiAndRender(adapter);
        return view;
    }
    void callApiAndRender(TreatmentAdapter adapter){
        PatientService service = new PatientService();
        service.getMedicalHistory(Store.get_instance().getPatientId(), new OnSuccessResponse<Treatment[]>() {
            @Override
            public void onSuccess(Treatment[] response) {
                Log.d("LONG", new Gson().toJson(response));
                adapter.setData(new ArrayList<>(Arrays.asList(response)));
            }
        });
    }
}