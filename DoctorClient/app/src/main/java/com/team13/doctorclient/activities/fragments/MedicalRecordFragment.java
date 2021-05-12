package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.MedicalRecordAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.Prescription;
import com.team13.doctorclient.models.ServicePack;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView searchView;
    MedicalRecordAdapter medicalRecordAdapter;
    RecyclerView treatmentList;

    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalRecordFragment newInstance(String param1, String param2) {
        MedicalRecordFragment fragment = new MedicalRecordFragment();
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
        View view=inflater.inflate(R.layout.fragment_medical_record, container, false);
        treatmentList= view.findViewById(R.id.patient_list);
        medicalRecordAdapter= new MedicalRecordAdapter(view.getContext(),getTreatments());
        treatmentList.setAdapter(medicalRecordAdapter);
        treatmentList.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
    public ArrayList<Treatment> getTreatments(){
        ArrayList<Treatment> treatments= new ArrayList<>();
        ServicePack servicePack = new ServicePack("Beauty Care","no", 500000, "1");
        Appointment appointment = new Appointment("MN", servicePack, "Cough","7/04/2021","13:30","PROCESSING");
        //Prescription prescription = new Prescription();
//        for (int i=0;i<10;++i){
//            treatments.add(new Treatment(appointment, prescription));
//        }
        return treatments;
    }
}