package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.patientclient.R;
import com.team13.patientclient.adapters.DoctorListAdapter;
import com.team13.patientclient.models.Doctor;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.DoctorService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseDoctorFragment extends Fragment{
    DoctorListAdapter adapter;
    Listener listener;
    String mDoctorId;

    public ChooseDoctorFragment() {
        // Required empty public constructor
    }
    public static ChooseDoctorFragment newInstance(String param1, String param2) {
        ChooseDoctorFragment fragment = new ChooseDoctorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof ChooseDoctorFragment.Listener) {
            listener = (ChooseDoctorFragment.Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_doctor, container, false);
        adapter = new DoctorListAdapter(getContext());
        adapter.setListener(new DoctorListAdapter.Listener() {
            @Override
            public void onDoctorSelect(String doctorId) {
                mDoctorId = doctorId;
                if (listener != null) listener.goToSchedulePick(mDoctorId);
            }
        });

        RecyclerView doctorListRCV = view.findViewById(R.id.doctor_recycleview);
        doctorListRCV.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        doctorListRCV.setAdapter(adapter);


        getDataAndRender(view);
        return view;
    }

    private void getDataAndRender(View view) {
        if (listener == null) return;
        String serviceId = listener.getServiceId();
        DoctorService service = new DoctorService();
        service.getDoctorsByServices(serviceId, new OnSuccessResponse<Doctor[]>() {
            @Override
            public void onSuccess(Doctor[] response) {
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                adapter.setDoctors(new ArrayList<>(Arrays.asList(response)));
            }
        });

    }

    public interface Listener{
        String getServiceId();
        void goToSchedulePick(String id);
    }
}