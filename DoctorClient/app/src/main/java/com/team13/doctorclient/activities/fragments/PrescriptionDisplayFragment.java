package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.PatientModel;
import com.team13.doctorclient.models.Prescription;


public class PrescriptionDisplayFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Prescription prescription;
    private PatientModel patientModel;

    public PrescriptionDisplayFragment() {
        // Required empty public constructor
    }

    public static PrescriptionDisplayFragment newInstance(Prescription prescription, PatientModel patientModel) {
        PrescriptionDisplayFragment fragment = new PrescriptionDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, prescription);
        args.putSerializable(ARG_PARAM2, patientModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prescription = (Prescription)getArguments().getSerializable(ARG_PARAM1);
            patientModel = (PatientModel)getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prescription_display, container, false);
        TextView text = view.findViewById(R.id.prescription_patient_name);
        text.setText(patientModel.getName());
        text = view.findViewById(R.id.prescription_patient_symptom);
        text.setText(prescription.getSymptom());
        text = view.findViewById(R.id.prescription_patient_diagnose);
        text.setText(prescription.getDiagnose());
//        text = view.findViewById(R.id.prescription_patient_date);
        RecyclerView drugList = view.findViewById(R.id.drug_list);
        DrugAdapter adapter = new DrugAdapter(view.getContext(), prescription.getDrugList(), false);
        drugList.setAdapter(adapter);
        drugList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        text = view.findViewById(R.id.prescription_note);
        text.setText(prescription.getNote());
//        text = view.findViewById(R.id.prescription_doctor);
        return view;
    }
}