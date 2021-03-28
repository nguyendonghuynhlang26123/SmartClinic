package com.team13.patientclient.activities.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.CircularImageView;
=======
import android.widget.DatePicker;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
>>>>>>> da2be45a5b634ba10bc0dbdbf48acd54c911c850
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.PatientModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int selectedYear = 2000;
    int selectedMonth = 5;
    int selectedDayOfMonth = 10;
    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // Inflate the layout for this fragment
<<<<<<< HEAD
        EditText dobInput = view.findViewById(R.id.profile_edit_dob);
        EditText nameInput = view.findViewById(R.id.profile_edit_name);
        CircularImageView avatarView = view.findViewById(R.id.profile_edit_avatar);
        RadioGroup genderRadioGroup = view.findViewById(R.id.profile_edit_gender);

        AccountModel accountData = Store.get_instance().getUserAccount();
        PatientModel userProfile = accountData.getUserInfor();

        nameInput.setText(userProfile.getName());

        //if (accountData.getUserInfor().getWeight() != 0) ((EditText) view.findViewById(R.id.profile_edit_name)).setText(accountData.getUserInfor().getWeight() + "kg");
        if (accountData.getUserInfor().getDateOfBirth() != 0) dobInput.setText(userProfile.getDateOfBirth() + "");
        if (accountData.equals("Male")) genderRadioGroup.check(R.id.profile_edit_gender_male);
        else if (accountData.equals("FeMale")) genderRadioGroup.check(R.id.profile_edit_gender_female);
        Picasso.get().load(accountData.getUserInfor().getAvatarUrl()).into((ImageView) view.findViewById(R.id.profile_edit_avatar));

        view.findViewById(R.id.profile_edit_save_button).setOnClickListener(v -> {
            //TODO: GET LOCAL DATA AND CALL PUT REQUEST
=======
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        TextInputEditText editTextDate = view.findViewById(R.id.input_birth);
        editTextDate.setOnClickListener(v->{
            DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
            datePickerDialog.show();
>>>>>>> da2be45a5b634ba10bc0dbdbf48acd54c911c850
        });
        return view;
    }
}