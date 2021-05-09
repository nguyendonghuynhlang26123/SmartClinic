package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.LoginActivity;
import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.AuthService;

import java.util.Objects;

public class LoginFragment extends Fragment {

    TextInputEditText phoneInput;
    TextInputEditText passwordInput;
    ProgressBar progressBar;
    Button loginButton;
    Button signUpButton;
    Listener listener;

    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        if (context instanceof LoginFragment.Listener){
            listener = (LoginFragment.Listener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement SchedulePickFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        passwordInput = view.findViewById(R.id.input_password);
        phoneInput = view.findViewById(R.id.input_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        signUpButton = view.findViewById(R.id.sign_up_text_button);
        signUpButton.setOnClickListener(v -> ((LoginActivity) Objects.requireNonNull(getActivity())).setSignUpFragment());
        progressBar = view.findViewById(R.id.progress);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String phone = Utils.unFormatPhoneNumber(Objects.requireNonNull(phoneInput.getText()).toString());
            String password = Objects.requireNonNull(passwordInput.getText()).toString();
            if (phone.isEmpty() || password.isEmpty())
                Toast.makeText(getContext(), "Empty Input! Please try again", Toast.LENGTH_SHORT).show();
            else verifyAndProcess(phone, password, view);
        });

        return view;
    }

    private void verifyAndProcess(String phone, String password, View view) {
        progressBar.setVisibility(View.VISIBLE);
        AuthService auth = new AuthService();
        auth.login("+84" + phone, password, new OnResponse<AccountModel>() {
            @Override
            public void onRequestSuccess(AccountModel account) {
                Store.get_instance().setUserAccount(account);
                progressBar.setVisibility(View.INVISIBLE);
                listener.startProgram();
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                progressBar.setVisibility(View.INVISIBLE);
                TextView textError = view.findViewById(R.id.text_error);
                textError.setText(response.getMessage());
                textError.setVisibility(View.VISIBLE);
            }
        });
    }

    public interface Listener {
        void startProgram();
    }

}