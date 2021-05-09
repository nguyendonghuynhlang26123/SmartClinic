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
import com.team13.patientclient.adapters.ServicePackAdapter;
import com.team13.patientclient.models.ServicePack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceDisplayFragment extends Fragment {

    ArrayList<ServicePack> servicePacks;
    ServiceDisplayListener listener;
    public ServiceDisplayFragment() {
        // Required empty public constructor
    }

    public static ServiceDisplayFragment newInstance() {
        ServiceDisplayFragment fragment = new ServiceDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servicePacks = listener.getDisplayService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_display, container, false);
        RecyclerView serviceList = view.findViewById(R.id.service_list);
        ServicePackAdapter adapter = new ServicePackAdapter(view.getContext(), servicePacks);
        serviceList.setAdapter(adapter);
        serviceList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
//        serviceList.setOnFlingListener(new RecyclerView.OnFlingListener() {
//            private static final int SWIPE_VELOCITY_THRESHOLD = 2000;
//            @Override
//            public boolean onFling(int velocityX, int velocityY) {
//                if(Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD){
//                    if(velocityY<0){
//                        // Swipe down
//                    } else {
//                        // Swipe up
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        return view;
    }

    public interface ServiceDisplayListener{
        ArrayList<ServicePack> getDisplayService();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof ServiceDisplayListener) {
            listener = (ServiceDisplayListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ServiceDisplayListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}