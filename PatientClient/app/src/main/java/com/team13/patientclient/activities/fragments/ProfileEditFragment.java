package com.team13.patientclient.activities.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.CircularImageView;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.ImagePickingActivity;
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
    View view;

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
        this.view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // Inflate the layout for this fragment
        TextInputEditText nameInput = view.findViewById(R.id.profile_edit_name);
        TextInputEditText editTextDate = view.findViewById(R.id.input_birth);
        CircularImageView avatarView = view.findViewById(R.id.profile_edit_avatar);
        RadioGroup genderRadioGroup = view.findViewById(R.id.profile_edit_gender);
        TextInputEditText weightInput = view.findViewById(R.id.profile_edit_weight);

        PatientModel userProfile = Store.get_instance().getUserAccount().getUserInfor();

        nameInput.setText(userProfile.getName());

        if (userProfile.getDateOfBirth() != 0) editTextDate.setText(userProfile.getDateOfBirthString() + "");
        if (userProfile.getWeight() != 0) editTextDate.setText(userProfile.getWeight() + "");
        if (userProfile.getGender().equals("Male")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_male)).setChecked(true);
        else if (userProfile.getGender().equals("Female")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_female)).setChecked(true);
        Picasso.get().load(userProfile.getAvatarUrl()).into(avatarView);

        avatarView.setOnClickListener(l->{
            choosingAvatarHandler();
        });

        editTextDate.setOnClickListener(v->{
            showDatePickerDialog(editTextDate);
        });

        view.findViewById(R.id.profile_edit_save_button).setOnClickListener(v ->{
            String name  = nameInput.getText().toString();
            String avatarURI = avatarURL == null ? userProfile.getAvatarUrl() : avatarURL.toString();
            String gender = genderRadioGroup.getCheckedRadioButtonId() == -1 ? "" : (genderRadioGroup.getCheckedRadioButtonId() == R.id.profile_edit_gender_male) ? "Male" : "Female";
            long dob = Utils.dateStringToNumber(editTextDate.getText().toString());
            double weight = Double.parseDouble(weightInput.getText().toString());

            saveHandle(userProfile, name, avatarURI, gender, dob, weight);
        });
        return view;
    }

    private void saveHandle(PatientModel userProfile, String name, String avatarURI, String gender, long dob, double weight) {
        if (name.isEmpty()){
            Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.checkValidPatientName(name) ){
            Toast.makeText(getContext(), "Name is too long! Name is no longer than " + Utils.NAME_LENGTH_LIMIT + " characters!", Toast.LENGTH_SHORT).show();
        }

        //TODO: UPLOAD IMAGE TO FIREBASE then call api!
//        StorageTask task = is.uploadFile(img, FoodDialog.this.getActivity().getContentResolver()).addOnSuccessListener(t ->{
//            t.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( uri ->{
//                //UPload
        PatientModel updatedModel = new PatientModel(name, avatarURI, gender, dob, weight);
        callPutApi(userProfile, updatedModel);
//            });
//        }).addOnFailureListener(t -> {
//            binding.foodModifyDialog.setVisibility(View.VISIBLE);
//            binding.foodProcessDialog.setVisibility(View.GONE);
//
//            Snackbar.make(view, "Upload Failed", Snackbar.LENGTH_LONG ).show();
//        });


    }

    private void callPutApi(PatientModel userProfile, PatientModel updatedModel) {
        PatientService patientService = new PatientService();
        patientService.updatePatientProfile(userProfile.getId(), updatedModel, new OnResponse<Void>() {
            @Override
            public void onRequestSuccess(Void response) {
                Toast.makeText(getContext(),"Update successfully", Toast.LENGTH_SHORT).show();

                userProfile.setName(updatedModel.getName());
                userProfile.setAvatarUrl(updatedModel.getAvatarUrl());
                userProfile.setDateOfBirth(updatedModel.getDateOfBirth());
                userProfile.setGender(updatedModel.getGender());
                userProfile.setWeight(updatedModel.getWeight());
            }
        });
    }

    private void showDatePickerDialog(TextInputEditText editTextDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
        datePickerDialog.show();
    }

    private void choosingAvatarHandler() {
        Intent intent = ImagePickingActivity.fileChoserIntent();
        startActivityForResult(intent, ImagePickingActivity.PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== ImagePickingActivity.PICK_IMAGE_REQUEST && resultCode == -1 //OK STATUS
                && data != null && data.getData() != null){
            avatarURL = data.getData();
            Picasso.get().load(avatarURL).into((ImageView) this.view.findViewById(R.id.profile_edit_avatar));
        }
    }
}