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
import com.team13.patientclient.models.DrugModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<DrugModel> drugList;

    public DrugListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugListFragment newInstance(ArrayList<DrugModel> param1) {
        DrugListFragment fragment = new DrugListFragment();
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