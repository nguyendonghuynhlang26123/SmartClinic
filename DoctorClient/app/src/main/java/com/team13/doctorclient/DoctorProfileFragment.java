package com.team13.doctorclient;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.team13.doctorclient.models.Doctor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Doctor doctor;
    Context context;
    ChipGroup chips;
    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorProfileFragment newInstance(String param1, String param2) {
        DoctorProfileFragment fragment = new DoctorProfileFragment();
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
        getDoctor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        context=view.getContext();
        ((TextView)view.findViewById(R.id.doctor_name)).setText(doctor.getDoctorName());
        ((TextView)view.findViewById(R.id.doctor_about)).setText(doctor.getAbout());
        chips=view.findViewById(R.id.chips);
        renderSpecialties(doctor.getSpecialties());
        ImageButton editBtn=view.findViewById(R.id.profile_edit_button);
        editBtn.setOnClickListener(v -> {
            Fragment fragment= EditProfileDoctorFragment.newInstance(doctor);
            loadFragment(fragment);
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void renderSpecialties(String specialties){
        String[] temp=specialties.split(",");

        for (String s:temp){
            Chip chip = new Chip(context);
            chip.setText(s);
            chips.addView(chip);
        }
    }
    private  void getDoctor(){
        doctor= new Doctor("001","Dr CoCo");
        doctor.setAbout("Doctors tend to be predominantly investigative individuals, which means that they are quite inquisitive and curious people that often like to spend time alone with ..");
        doctor.setSpecialties("Pediatric,Surgery,Cold");
    }
}