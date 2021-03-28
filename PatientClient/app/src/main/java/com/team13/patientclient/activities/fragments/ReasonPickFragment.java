package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team13.patientclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReasonPickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReasonPickFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ReasonPickFragmentListener listener;
    public ReasonPickFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReasonPickFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReasonPickFragment newInstance(String param1, String param2) {
        ReasonPickFragment fragment = new ReasonPickFragment();
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
    public void onAttach(Context context) {
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