package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Doctor;


public class EditProfileDoctorFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private Doctor doctor;
    ChipGroup chips;
    Context context;

    public EditProfileDoctorFragment() {
        // Required empty public constructor
    }

    public static EditProfileDoctorFragment newInstance(Doctor doctor) {
        EditProfileDoctorFragment fragment = new EditProfileDoctorFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, doctor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = (Doctor)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_edit_profile_doctor, container, false);
        context = view.getContext();
        EditText name = view.findViewById(R.id.doctor_name);
        name.setText(doctor.getDoctorName());
        EditText about = view.findViewById(R.id.doctor_about);
        about.setText(doctor.getBio());
        chips = view.findViewById(R.id.chips);
        renderSpecialties(doctor.getDepartment());
        AppCompatEditText inputChip = view.findViewById(R.id.input_chip);

        inputChip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    if(editable.toString().endsWith("\n")){
                        Chip chip = new Chip(context);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(v -> {
                            chips.removeView(chip);
                        });
                        chip.setText(editable.toString());
                        chips.addView(chip);
                        inputChip.setText("");
                    }
                }

            }
        });
        view.findViewById(R.id.save_btn).setOnClickListener(v->{
            doctor.setDepartment(saveSpecialties());
            doctor.setDoctorName(name.getText().toString());
            doctor.setBio(about.getText().toString());
            getFragmentManager().popBackStack();
        });
        return view;
    }

    private void renderSpecialties(String specialties){
        String[] temp=specialties.split(",");

        for (String s:temp){
            Chip chip = new Chip(context);
            chip.setText(s);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                chips.removeView(chip);
            });
            chips.addView(chip);
        }
    }

    String saveSpecialties(){
        String result = "";
        for(int i = 0; i<chips.getChildCount(); ++i){
            Chip chip = (Chip)chips.getChildAt(i);
            result += chip.getText().toString()+",";
        }
        return result.substring(0,result.length()-1);
    }

    void callPutApi(Doctor doctorProfile, Doctor updateProfile){

    }
}