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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<ServicePack> servicePacks;
    ServiceDisplayListener listener;
    public ServiceDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceDisplayFragment newInstance(String param1, String param2) {
        ServiceDisplayFragment fragment = new ServiceDisplayFragment();
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
        serviceList.setOnFlingListener(new RecyclerView.OnFlingListener() {
            private static final int SWIPE_VELOCITY_THRESHOLD = 2000;
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if(Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD){
                    if(velocityY<0){
                        // Swipe down
                    } else {
                        // Swipe up
                    }
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public interface ServiceDisplayListener{
        ArrayList<ServicePack> getDisplayService();
    }

    @Override
    public void onAttach(Context context) {
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