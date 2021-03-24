package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.team13.patientclient.activities.MainActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.LoginActivity;
import com.team13.patientclient.models.Hospital;
import com.team13.patientclient.repository.services.HospitalService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText phoneInput;
    Button loginButton;
    Button signUpButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        phoneInput = view.findViewById(R.id.input_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        signUpButton = view.findViewById(R.id.sign_up_text_button);
        signUpButton.setOnClickListener(v -> {
            ((LoginActivity)getActivity()).setSignUpFragment();
        });
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), MainActivity.class);
            startActivity(i);
        });

        test();
        return view;
    }

    private void test() {
        final HospitalService hospitalService = new HospitalService();

        hospitalService.getHospital("6056b843cefabf3368f043cf").enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                if(!response.isSuccessful()){
                    Log.d("LONG","ERROR");
                    return;
                }

                Hospital data = response.body();
                Log.d("LONG", new Gson().toJson(data));
            }

            @Override
            public void onFailure(Call<Hospital> call, Throwable t) {
                Log.d("LONG","ERROR " + t.getMessage());
            }
        });
    }

}