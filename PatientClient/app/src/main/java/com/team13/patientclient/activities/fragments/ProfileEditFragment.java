package com.team13.patientclient.activities.fragments;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.CircularImageView;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.PatientModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.PatientService;

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
    Uri avatarURL;

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
        TextInputEditText nameInput = view.findViewById(R.id.profile_edit_name);
        TextInputEditText editTextDate = view.findViewById(R.id.input_birth);
        CircularImageView avatarView = view.findViewById(R.id.profile_edit_avatar);
        RadioGroup genderRadioGroup = view.findViewById(R.id.profile_edit_gender);

        PatientModel userProfile = Store.get_instance().getUserAccount().getUserInfor();

        nameInput.setText(userProfile.getName());

        if (userProfile.getDateOfBirth() != 0) editTextDate.setText(userProfile.getDateOfBirthString() + "");
        if (userProfile.getGender().equals("Male")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_male)).setChecked(true);
        else if (userProfile.getGender().equals("Female")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_female)).setChecked(true);
        Picasso.get().load(userProfile.getAvatarUrl()).into(avatarView);

        editTextDate.setOnClickListener(v->{
            DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
            datePickerDialog.show();
        });

        view.findViewById(R.id.profile_edit_save_button).setOnClickListener(v ->{
            String name  = nameInput.getText().toString();
            String avatarURI = avatarURL == null ? userProfile.getAvatarUrl() : avatarURL.toString();
            String gender = genderRadioGroup.getCheckedRadioButtonId() == -1 ? "" : (genderRadioGroup.getCheckedRadioButtonId() == R.id.profile_edit_gender_male) ? "Male" : "Female";
            long dob = Utils.dateStringToNumber(editTextDate.getText().toString());
            double weight = 0;

            if (name.isEmpty()){
                Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            PatientService patientService = new PatientService();
            patientService.updatePatientProfile(userProfile.getId(), new PatientModel(name, avatarURI, gender, dob, weight), new OnResponse<Void>() {
                @Override
                public void onRequestSuccess(Void response) {
                    Toast.makeText(getContext(),"Update successfully", Toast.LENGTH_SHORT).show();

                    userProfile.setName(name);
                    userProfile.setAvatarUrl(avatarURI);
                    userProfile.setDateOfBirth(dob);
                    userProfile.setGender(gender);
                    userProfile.setWeight(weight);
                }
            });
        });
        return view;
    }
}