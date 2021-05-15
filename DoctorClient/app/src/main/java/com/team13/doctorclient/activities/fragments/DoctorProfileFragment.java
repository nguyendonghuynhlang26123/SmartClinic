package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.models.AccountModel;
import com.team13.doctorclient.models.Doctor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorProfileFragment extends Fragment {

    View view;
    Context context;
    ChipGroup chips;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    public static DoctorProfileFragment newInstance() {
        DoctorProfileFragment fragment = new DoctorProfileFragment();
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
        view= inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        context=view.getContext();
        ImageButton editBtn=view.findViewById(R.id.profile_edit_button);
        editBtn.setOnClickListener(v -> {
            Fragment fragment= EditProfileDoctorFragment.newInstance(Store.get_instance().getUserAccount().getUserInfor());
            loadFragment(fragment);
        });
        view.findViewById(R.id.log_out_button).setOnClickListener(v->getActivity().finish());
        renderViewData();
        return view;
    }

    @Override
    public void onResume() {
        renderViewData();
        super.onResume();
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

    void renderViewData(){
        AccountModel accountModel = Store.get_instance().getUserAccount();
        Doctor doctor = accountModel.getUserInfor();
        ((TextView)view.findViewById(R.id.doctor_name)).setText(doctor.getDoctorName());
        ((TextView)view.findViewById(R.id.doctor_about)).setText(doctor.getBio());
        Picasso.get().load(doctor.getAvatarUrl()).into((ImageView) view.findViewById(R.id.doctor_avatar));
        chips=view.findViewById(R.id.chips);
        renderSpecialties(doctor.getDepartment());
    }

}