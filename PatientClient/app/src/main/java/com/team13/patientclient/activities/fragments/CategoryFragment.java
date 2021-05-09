package com.team13.patientclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.patientclient.R;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.Category;
import com.team13.patientclient.models.DrugModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<DrugModel> drugList;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(ArrayList<DrugModel> param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drugList = (ArrayList<DrugModel>)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView drugs = view.findViewById(R.id.category_item_list);
        drugs.setAdapter(new PharmacyItemAdapter(view.getContext(),drugList,true));
        drugs.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        return view;
    }

}