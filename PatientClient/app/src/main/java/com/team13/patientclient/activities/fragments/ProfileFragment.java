package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.Cart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton cartButton;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        cartButton = view.findViewById(R.id.service_cart);
        cartButton.setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), Cart.class);
            startActivity(i);
        });
        view.findViewById(R.id.profile_edit_button).setOnClickListener(v->{
            ProfileEditFragment profileEditFragment = new ProfileEditFragment();
            assert getFragmentManager() != null;
            profileEditFragment.show(getFragmentManager(), profileEditFragment.getTag());
        });

        //Render view data
        AccountModel accountData = Store.get_instance().getUserAccount();

        ((TextView) view.findViewById(R.id.profile_name)).setText(accountData.getUserInfor().getName());
        if (accountData.getUserInfor().getWeight() != 0) ((TextView) view.findViewById(R.id.profile_name)).setText(accountData.getUserInfor().getWeight() + "kg");
        if (accountData.getUserInfor().getDateOfBirth() != 0) ((TextView) view.findViewById(R.id.profile_age)).setText(accountData.getUserInfor().getDateOfBirth() + "");
        Picasso.get().load(accountData.getUserInfor().getAvatarUrl()).into((ImageView) view.findViewById(R.id.profile_avatar));

        return view;
    }

}