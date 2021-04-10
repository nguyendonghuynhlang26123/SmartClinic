package com.team13.patientclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.adapters.PrescriptionItemAdpater;
import com.team13.patientclient.models.DrugDetail;
import com.team13.patientclient.models.Prescription;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Prescription prescription;

    public PrescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrescriptionFragment newInstance(Prescription prescription) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, prescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prescription = (Prescription)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);
        RecyclerView drugList = view.findViewById(R.id.drug_list);
        PrescriptionItemAdpater adapter = new PrescriptionItemAdpater(view.getContext(),prescription.getDrugList());
        drugList.setAdapter(adapter);
        drugList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        TextView name = view.findViewById(R.id.prescription_patient_name);
        name.setText(Store.get_instance().getUserAccount().getUserInfor().getName());
        TextView symptom = view.findViewById(R.id.prescription_patient_symptom);
        symptom.setText(prescription.getSymptom());
        TextView diagnose = view.findViewById(R.id.prescription_patient_diagnose);
        diagnose.setText(prescription.getDiagnose());
        TextView date = view.findViewById(R.id.prescription_patient_date);
        //date.setText(prescription.getDate());
        TextView note = view.findViewById(R.id.prescription_note);
        note.setText(prescription.getNote());
        TextView doctor = view.findViewById(R.id.prescription_doctor);
        //doctor.setText(prescription.getDoctorName());
        return view;
    }

}