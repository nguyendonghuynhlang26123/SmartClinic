package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.DrugModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugsDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugsDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView commonDrugList;
    RecyclerView insuranceDrugList;
    RecyclerView othersDrugList;
    DrugDisplayListener listener;
    ArrayList<DrugModel> data;
    public DrugsDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrugsDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugsDisplayFragment newInstance(String param1, String param2) {
        DrugsDisplayFragment fragment = new DrugsDisplayFragment();
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
        data = listener.getDisplayData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drugs_display, container, false);
        commonDrugList = view.findViewById(R.id.common_drug);
        PharmacyItemAdapter pharmacyItemAdapter = new PharmacyItemAdapter(view.getContext(), data);

        commonDrugList.setAdapter(pharmacyItemAdapter);
        commonDrugList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        insuranceDrugList = view.findViewById(R.id.insurance_drug);
        insuranceDrugList.setAdapter(pharmacyItemAdapter);
        insuranceDrugList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        othersDrugList = view.findViewById(R.id.others_drug);
        othersDrugList.setAdapter(pharmacyItemAdapter);
        othersDrugList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    public interface DrugDisplayListener{
        ArrayList<DrugModel> getDisplayData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DrugDisplayListener) {
            listener = (DrugDisplayListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DrugDisplayListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}