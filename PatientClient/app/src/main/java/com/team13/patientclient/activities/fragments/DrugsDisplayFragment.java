package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.Category;
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
    DrugDisplayListener listener;
    ArrayList<Category> data;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drugs_display, container, false);
        LinearLayout categoryListLayout = view.findViewById(R.id.category_list);
        data.forEach(category -> {
            View categoryView = LayoutInflater.from(view.getContext()).inflate(R.layout.drug_category_list,null);
            PharmacyItemAdapter pharmacyItemAdapter = new PharmacyItemAdapter(categoryView.getContext(), category.getDrugList());
            RecyclerView drugList = categoryView.findViewById(R.id.category_item_list);
            drugList.setAdapter(pharmacyItemAdapter);
            drugList.setLayoutManager(new LinearLayoutManager(categoryView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            TextView categoryName = categoryView.findViewById(R.id.category_name);
            categoryName.setText(category.getName());
            categoryListLayout.addView(categoryView);
        });
        return view;
    }

    public interface DrugDisplayListener{
        ArrayList<Category> getDisplayData();
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