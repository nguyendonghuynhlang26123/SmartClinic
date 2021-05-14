package com.team13.doctorclient.activities.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.models.Doctor;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.DoctorService;
import com.team13.doctorclient.repositories.services.ImageService;


public class EditProfileDoctorFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    ProgressDialog dialog;
    private Doctor doctor;
    ChipGroup chips;
    Context context;

    ImageView imageView;
    Uri avatarURL;

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
        dialog = new ProgressDialog(getContext());
        View view= inflater.inflate(R.layout.fragment_edit_profile_doctor, container, false);
        context = view.getContext();
        EditText name = view.findViewById(R.id.doctor_name);
        name.setText(doctor.getDoctorName());
        EditText about = view.findViewById(R.id.doctor_about);
        about.setText(doctor.getBio());
        chips = view.findViewById(R.id.chips);
        renderSpecialties(doctor.getDepartment());
        AppCompatEditText inputChip = view.findViewById(R.id.input_chip);
        imageView = view.findViewById(R.id.doctor_avatar);
        Picasso.get().load(doctor.getAvatarUrl()).into(imageView);
        imageView.setOnClickListener(v -> handleOnAvatarClicked());

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

            dialog.show();
            dialog.setCancelable(false);
            if (avatarURL != null) uploadImage();
            else saveHandle(doctor);
        });

        return view;
    }

    private void uploadImage() {
        ImageService is = new ImageService();
        String msg = "Upload image to server";
        dialog.setMessage(msg);
        StorageTask task = is.uploadFile(avatarURL, this.getActivity().getContentResolver()).addOnSuccessListener(t ->{
            t.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( uri ->{
                //UPload complete
                doctor.setAvatarUrl(uri.toString());
                saveHandle(doctor);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Upload Image Failed! The process is canceled", Toast.LENGTH_LONG ).show();
            dialog.dismiss();
        });
    }

    private void handleOnAvatarClicked() {
        Intent i = ImageService.fileChoserIntent();
        startActivityForResult(i, ImageService.PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== ImageService.PICK_IMAGE_REQUEST && resultCode == -1 //OK STATUS
                && data != null && data.getData() != null){

            avatarURL = data.getData();
            Picasso.get().load(avatarURL).into(imageView);
        }
    }

    private void saveHandle(Doctor data) {
        dialog.setMessage("Update doctor data...");
        DoctorService service = new DoctorService();
        service.updateDoctor(Store.get_instance().getId(), data, new OnResponse<Doctor>() {
            @Override
            public void onRequestSuccess(Doctor response) {
                Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
                dialog.dismiss();
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
                dialog.dismiss();
            }
        });
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


}