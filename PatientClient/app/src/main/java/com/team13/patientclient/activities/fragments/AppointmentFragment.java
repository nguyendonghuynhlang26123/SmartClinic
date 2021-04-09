package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.AppointmentItemAdapter;
import com.team13.patientclient.adapters.TreatmentAdapter;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.DrugDetail;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;
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
            public void onItemClick(Treatment.Prescription prescription) {
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(prescription);
                fragment.show(getFragmentManager(),fragment.getTag());
            }

            @Override
            public void onHasAppointment() {
                view.findViewById(R.id.add_appointment_button).setVisibility(View.INVISIBLE);
                notify.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAppointmentRemove() {
                notify.setVisibility(View.GONE);
            }
        }, getEmptyTreatment());
        treatmentList.setAdapter(adapter);
        treatmentList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        view.findViewById(R.id.add_appointment_button).setOnClickListener(v->{
            Intent i = new Intent(view.getContext(), ServiceActivity.class);
            startActivity(i);
        });
        return view;
    }

    ArrayList<Treatment> getEmptyTreatment(){
        ArrayList<Treatment> treatments = new ArrayList<>(8);
        treatments.add(new Treatment("5","General Care","4/1/2021","17:30","A","Dr.Coco","B","MN","CANCEL"));
        treatments.add(new Treatment("4","General Care","3/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("3","General Care","2/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("2","General Care","1/1/2021","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        treatments.add(new Treatment("1","General Care","31/12/2020","17:30","A","Dr.Coco","B","MN","PROCESSED"));
        ArrayList<DrugDetail> testDrugList= new ArrayList<DrugDetail>(Collections.singletonList(new DrugDetail(new DrugModel(), 2, "Morning: 2")));
        for(Treatment treatment: treatments){
            treatment.createPrescription(testDrugList,"Comeback at 3/3/2021","No","Sleep");
        }

        treatments.add(0, new Treatment("1","General Care","5/1/2021","17:30","A","Dr.Coco","B","MN","PENDING"));
        return treatments;
    }
}