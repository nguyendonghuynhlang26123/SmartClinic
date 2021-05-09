package com.team13.patientclient.activities.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.models.AccountModel;

import java.util.Locale;

public class ProfileFragment extends Fragment {
    View view;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
         
        view.findViewById(R.id.profile_edit_button).setOnClickListener(v->{
            ProfileEditFragment profileEditFragment = new ProfileEditFragment();
            assert getFragmentManager() != null;
            profileEditFragment.show(getFragmentManager(), profileEditFragment.getTag());
        });
        view.findViewById(R.id.service_treatment).setOnClickListener(v->{
        });
        view.findViewById(R.id.fun_about_trailing).setOnClickListener(v->{
            String[] students = {"18127003","18127016","18127136","18127269"};
            new MaterialAlertDialogBuilder(view.getContext())
                    .setTitle("Team 13")
                    .setItems(students, (dialog, which) -> {

                    })
                    .show();
        });
        //Render view data
        renderViewData(view);

        return view;
    }

    private void renderViewData(View view) {
        AccountModel accountData = Store.get_instance().getUserAccount();

        ((TextView) view.findViewById(R.id.profile_name)).setText(accountData.getUserInfor().getName());
        if (accountData.getUserInfor().getWeight() != 0) ((TextView) view.findViewById(R.id.profile_weight)).setText(String.format("%s", accountData.getUserInfor().getWeight()));
        if (accountData.getUserInfor().getDateOfBirth() != 0) ((TextView) view.findViewById(R.id.profile_age)).setText(String.format(Locale.US,"%d", accountData.getUserInfor().getAge()));
        if (!accountData.getUserInfor().getGender().isEmpty()) ((TextView) view.findViewById(R.id.profile_gender)).setText(accountData.getUserInfor().getGender());
        Picasso.get().load(accountData.getUserInfor().getAvatarUrl()).into((ImageView) view.findViewById(R.id.profile_avatar));
    }

}