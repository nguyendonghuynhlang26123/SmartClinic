package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.MedicalRecordAdapter;
import com.team13.doctorclient.models.PatientPageModel;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.PatientService;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalRecordFragment extends Fragment {
    SearchView searchView;
    MedicalRecordAdapter medicalRecordAdapter;
    RecyclerView treatmentList;
    PatientService service;

    String searchText;
    PatientPageModel patientPage;

    public MedicalRecordFragment() {
        // Required empty public constructor
    }
    public static MedicalRecordFragment newInstance(String param1, String param2) {
        MedicalRecordFragment fragment = new MedicalRecordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        service = new PatientService();
        View view=inflater.inflate(R.layout.fragment_medical_record, container, false);
        treatmentList= view.findViewById(R.id.patient_list);
        medicalRecordAdapter= new MedicalRecordAdapter(view.getContext());
        treatmentList.setAdapter(medicalRecordAdapter);
//        treatmentList.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        treatmentList.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        view.findViewById(R.id.loadmore_btn).setOnClickListener(v-> {
            callApiAndRender(patientPage.getCurPage()+1, view, searchText);
        });

        searchView = view.findViewById(R.id.search_patient);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                callApiAndRender(1, view, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchText = newText;
                    callApiAndRender(1, view, null);
                }
                return false;
            }
        });

        callApiAndRender(1, view, null);
        return view;
    }

    void handleButtonStatus(View view){
        if (patientPage.getCurPage() < patientPage.getTotalPage()) view.findViewById(R.id.loadmore_btn).setEnabled(true);
        else view.findViewById(R.id.loadmore_btn).setEnabled(false);
    }

    private void callApiAndRender(int page, View view, String searchText) {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        if (searchText == null) {
            service.getPatientsByPage(page, new OnSuccessResponse<PatientPageModel>() {
                @Override
                public void onSuccess(PatientPageModel response) {
                    patientPage = response;
                    medicalRecordAdapter.setData(new ArrayList<>(Arrays.asList(patientPage.getData())));
                    view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    handleButtonStatus(view);
                }
            });
        } else {
            service.searchPatients(searchText,page, new OnSuccessResponse<PatientPageModel>() {
                @Override
                public void onSuccess(PatientPageModel response) {
                    patientPage = response;
                    medicalRecordAdapter.setData(new ArrayList<>(Arrays.asList(patientPage.getData())));
                    view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    handleButtonStatus(view);
                }
            });
        }
    }

}