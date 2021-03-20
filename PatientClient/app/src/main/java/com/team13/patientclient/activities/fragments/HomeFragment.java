package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.team13.patientclient.activities.LocationActivity;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.DepartmentActivity;
import com.team13.patientclient.adapters.BannerAdapter;
import com.team13.patientclient.adapters.DepartmentItemAdapter;
import com.team13.patientclient.models.Department;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager viewPager;
    BannerAdapter bannerAdapter;
    DotsIndicator dotsIndicator;
    RecyclerView departmentList;
    RecyclerView pharmacyList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.news_banner);
        bannerAdapter = new BannerAdapter(view.getContext());
        viewPager.setAdapter(bannerAdapter);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);
        departmentList = view.findViewById(R.id.department_list);
        DepartmentItemAdapter departmentItemAdapter = new DepartmentItemAdapter(view.getContext(), getDepartments());
        departmentList.setAdapter(departmentItemAdapter);
        departmentList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        pharmacyList = view.findViewById(R.id.pharmacy_list);
        DepartmentItemAdapter departmentItemAdapter1 = new DepartmentItemAdapter(view.getContext(), getDepartments());
        pharmacyList.setAdapter(departmentItemAdapter1);
        pharmacyList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.findViewById(R.id.detail_department).setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), DepartmentActivity.class);
            startActivity(i);
        });
        view.findViewById(R.id.detail_pharmacy).setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), PharmacyActivity.class);
            startActivity(i);
        });
        view.findViewById(R.id.location_button).setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), LocationActivity.class);
            startActivity(i);
        });
        return view;
    }
    ArrayList<Department> getDepartments(){
        ArrayList<Department> departments = new ArrayList<>(7);
        for(int i=0;i<7;++i){
            departments.add(new Department("dep"));
        }
        return departments;
    }
}