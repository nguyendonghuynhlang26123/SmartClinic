package com.team13.doctorclient.activities.fragments;

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
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.models.AccountModel;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AuthService;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NurseLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NurseLoginFragment extends Fragment {

    TextInputEditText phoneInput;
    TextInputEditText passwordInput;
    ProgressBar progressBar;
    Button loginButton;
    NurseLoginFragment.Listener listener;

    public NurseLoginFragment() {
        // Required empty public constructor
    }

    public static NurseLoginFragment newInstance(String param1, String param2) {
        NurseLoginFragment fragment = new NurseLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DoctorLoginFragment.Listener){
            listener = (NurseLoginFragment.Listener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nurse_login, container, false);
        passwordInput = view.findViewById(R.id.input_password);
        phoneInput = view.findViewById(R.id.input_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
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
                if (account.getUserType().equals(Utils.USER_TYPE_NURSE)){
                    Store.get_instance().setUserAccount(account);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "LOGGED IN", Toast.LENGTH_LONG).show();
                    listener.startNurseActivity();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    TextView textError = view.findViewById(R.id.text_error);
                    textError.setText("Invalid credentials!");
                    textError.setVisibility(View.VISIBLE);
                }
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
        void startNurseActivity();
    }
}