package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.DrugDetail;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class ReviewPrescriptionFragment extends BottomSheetDialogFragment  {
    ReviewDrugListener listener;
    DrugAdapter drugAdapter;
    MaterialToolbar topAppBar;

    Treatment treatment;
    View view;

    public ReviewPrescriptionFragment() {
        // Required empty public constructor
    }


    public static ReviewPrescriptionFragment newInstance(Treatment treatment ) {
        ReviewPrescriptionFragment fragment = new ReviewPrescriptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.treatment = treatment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review_prescription, container, false);

        //views
        RecyclerView drugList = view.findViewById(R.id.drug_list);
        topAppBar = view.findViewById(R.id.topAppBar);

        //Adapters
        drugAdapter = new DrugAdapter(view.getContext(),false);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));

        rerender();

        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.apply:
                    listener.onApplyOldPrescription(treatment.getPrescription().getDrugList());
                    dismiss();
                    return true;

                case R.id.next:
                    treatment = listener.getNextTreatment();
                    rerender();
                    return true;
                default:
                    break;
            }
            return false;
        });

        topAppBar.setNavigationOnClickListener(v -> {
            treatment = listener.getPreviousTreatment();
            rerender();
        });
        return view;
    }

    void rerender(){
        ((TextView) view.findViewById(R.id.prescription_patient_name)).setText(treatment.getPatient().getName());
        ((TextView) view.findViewById(R.id.prescription_patient_symptom)).setText(treatment.getPrescription().getSymptom());
        ((TextView) view.findViewById(R.id.prescription_patient_diagnostic)).setText(treatment.getPrescription().getDiagnose());
        ((TextView) view.findViewById(R.id.prescription_patient_date)).setText(treatment.getDate());
        ((TextView) view.findViewById(R.id.prescription_patient_date)).setText(treatment.getDate());
        drugAdapter.setData(treatment.getPrescription().getDrugList());
    }


    public interface ReviewDrugListener{
        void onApplyOldPrescription(ArrayList<DrugDetail> addDrugs);
        Treatment getNextTreatment();
        Treatment getPreviousTreatment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DrugAddFragment.AddDrugListener) {
            listener= (ReviewPrescriptionFragment.ReviewDrugListener)context;
        }
        else {
//            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener= null;
    }
}