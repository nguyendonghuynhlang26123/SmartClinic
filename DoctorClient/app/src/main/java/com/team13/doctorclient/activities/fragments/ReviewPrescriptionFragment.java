package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Drug;
import com.team13.doctorclient.models.Prescription;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewPrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewPrescriptionFragment extends BottomSheetDialogFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ReviewDrugListener listener;
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    Prescription prescription;
    MaterialToolbar topAppBar;
    TextView name,symptom,diagnostic,dateStart,dateEnd;
    public ReviewPrescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ReviewPrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewPrescriptionFragment newInstance() {
        ReviewPrescriptionFragment fragment = new ReviewPrescriptionFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
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
        getPrescription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_review_prescription, container, false);
        drugList= view.findViewById(R.id.drug_list);
        drugAdapter =new DrugAdapter(view.getContext(),getDrug(),false);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        topAppBar=view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.apply:
                        //listener.onSaveListDrug(prescription.getDrugList());
                        return true;

                    case R.id.next:
                        // load next prescription
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get previous prescription
            }
        });
        return view;
    }
    public ArrayList<Drug> getDrug(){
        ArrayList<Drug> drugArrayList= new ArrayList<>(10);
        for(int i=0;i<5;++i){
            drugArrayList.add(new Drug("001","Panadol","3","Ngày 2 lần"));
        }
        return drugArrayList;
    }
    public void getPrescription(){
        // TODO
        //prescription= new Prescription("001","MN",getDrug(),"note here","cough","cough","07/04/2021","14/04/2021");
    }
    public interface ReviewDrugListener{
        void onSaveListDrug(ArrayList<Drug> addDrugs);
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