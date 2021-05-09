package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.team13.patientclient.R;

import org.jetbrains.annotations.NotNull;

public class ReasonPickFragment extends Fragment {

    ReasonPickFragmentListener listener;
    public ReasonPickFragment() {
        // Required empty public constructor
    }

    public static ReasonPickFragment newInstance() {
        ReasonPickFragment fragment = new ReasonPickFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reason_pick, container, false);
        TextView reason = view.findViewById(R.id.input_reason);
        TextInputLayout layout = view.findViewById(R.id.outlinedReasonField);
        int maxCounter = layout.getCounterMaxLength();
        view.findViewById(R.id.process_button).setOnClickListener(v->{
            String reasonText = reason.getText().toString();
            if(reasonText.trim().isEmpty()||reasonText.length() > maxCounter)
                Toast.makeText(view.getContext(),"Invalid reason", Toast.LENGTH_SHORT).show();
            else listener.gotoAppointmentConfirm(reason.getText().toString());
        });
        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof ReasonPickFragmentListener) {
            listener = (ReasonPickFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReasonPickFragmentListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ReasonPickFragmentListener{
        void gotoAppointmentConfirm(String reason);
    }
}