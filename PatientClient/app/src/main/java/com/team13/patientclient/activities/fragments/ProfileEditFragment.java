package com.team13.patientclient.activities.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.CircularImageView;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.models.PatientModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.ImageService;
import com.team13.patientclient.repository.services.PatientService;

import java.util.Locale;
import java.util.Objects;

public class ProfileEditFragment extends BottomSheetDialogFragment {
    Uri avatarURL;
    View view;
    ProfileEditListener listener;

    int selectedYear = 2000;
    int selectedMonth = 5;
    int selectedDayOfMonth = 10;

    public ProfileEditFragment() {
        // Required empty public constructor
    }
    public static ProfileEditFragment newInstance() {
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileEditListener) {
            listener = (ProfileEditListener) context;
        }else {
            throw new Error("Profile Fragment must implement Profile Edit Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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

        //TODO: RE RENDER AFTER EDIT
        nameInput.setText(userProfile.getName());

        if (userProfile.getDateOfBirth() != 0) editTextDate.setText(String.format("%s", userProfile.getDateOfBirthString()));
        if (userProfile.getWeight() != 0) weightInput.setText(String.format("%s", userProfile.getWeight()));
        if (userProfile.getGender().equals("Male")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_male)).setChecked(true);
        else if (userProfile.getGender().equals("Female")) ((RadioButton)view.findViewById(R.id.profile_edit_gender_female)).setChecked(true);
        Picasso.get().load(userProfile.getAvatarUrl()).into(avatarView);

        avatarView.setOnClickListener(l-> choosingAvatarHandler());

        editTextDate.setOnClickListener(v-> showDatePickerDialog(editTextDate));

        view.findViewById(R.id.profile_edit_save_button).setOnClickListener(v ->{
            String name  = Objects.requireNonNull(nameInput.getText()).toString();
            String gender = genderRadioGroup.getCheckedRadioButtonId() == -1 ? "" : (genderRadioGroup.getCheckedRadioButtonId() == R.id.profile_edit_gender_male) ? "Male" : "Female";
            long dob = Utils.dateStringToNumber(Objects.requireNonNull(editTextDate.getText()).toString());
            double weight = Double.parseDouble(Objects.requireNonNull(weightInput.getText()).toString());

            saveHandle(userProfile, name, gender, dob, weight);
        });
        return view;
    }

    private void saveHandle(PatientModel userProfile, String name, String gender, long dob, double weight) {
        if (name.isEmpty()){
            Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.checkValidPatientName(name) ){
            Toast.makeText(getContext(), "Name is too long! Name is no longer than " + Utils.NAME_LENGTH_LIMIT + " characters!", Toast.LENGTH_SHORT).show();
        }
        view.findViewById(R.id.profile_edit_save_button).setEnabled(false);

        if (avatarURL != null){
            ProgressBar progressBar = view.findViewById(R.id.progress_bar);
            ImageService is = new ImageService();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            //TODO: UPLOAD IMAGE TO FIREBASE then call api!
            StorageTask task = is.uploadFile(avatarURL, this.getActivity().getContentResolver()).addOnSuccessListener(t ->{
                t.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( uri ->{
                    //UPload
                    PatientModel updatedModel = new PatientModel(name, uri.toString(), gender, dob, weight);
                    callPutApi(userProfile, updatedModel);
                });
            }).addOnProgressListener(t->{
                double progress = (100.0 * t.getBytesTransferred() / t.getTotalByteCount());
                progressBar.setProgress((int) progress);
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Upload Image Failed! The process is canceled", Toast.LENGTH_LONG ).show();
                dismiss();
            });
        } else {
            PatientModel updatedModel = new PatientModel(name, userProfile.getAvatarUrl(), gender, dob, weight);
            callPutApi(userProfile, updatedModel);
        }

    }

    private void callPutApi(PatientModel userProfile, PatientModel updatedModel) {
        PatientService patientService = new PatientService();
        patientService.updatePatientProfile(userProfile.getId(), updatedModel, new OnSuccessResponse<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getContext(),"Update successfully", Toast.LENGTH_SHORT).show();

                userProfile.setName(updatedModel.getName());
                userProfile.setAvatarUrl(updatedModel.getAvatarUrl());
                userProfile.setDateOfBirth(updatedModel.getDateOfBirth());
                userProfile.setGender(updatedModel.getGender());
                userProfile.setWeight(updatedModel.getWeight());

                if (listener != null) listener.onUpdateSucceed();
                dismiss();
            }
        });
    }

    private void showDatePickerDialog(TextInputEditText editTextDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> editTextDate.setText(String.format(Locale.US,"%d/%d/%d", dayOfMonth, monthOfYear + 1, year));
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
        datePickerDialog.show();
    }

    private void choosingAvatarHandler() {
        Intent i = ImageService.fileChoserIntent();
        startActivityForResult(i, ImageService.PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== ImageService.PICK_IMAGE_REQUEST && resultCode == -1 //OK STATUS
                && data != null && data.getData() != null){

            avatarURL = data.getData();
            Picasso.get().load(avatarURL).into((ImageView) this.view.findViewById(R.id.profile_edit_avatar));
        }
    }

    public interface ProfileEditListener{
        void onUpdateSucceed();
    }
}