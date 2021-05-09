package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.OtpActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    TextInputEditText phoneInput;
    Button loginButton;
    SignUpListener listener;
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        phoneInput = view.findViewById(R.id.sign_up_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        loginButton = view.findViewById(R.id.login_text_button);
        loginButton.setOnClickListener(v -> listener.onBackToLogin());

        view.findViewById(R.id.sign_up_button).setOnClickListener(v ->{
            String phone = Utils.unFormatPhoneNumber(Objects.requireNonNull(phoneInput.getText()).toString());
            String name = ((TextView) view.findViewById(R.id.sign_up_name)).getText().toString();
            String password = ((TextView) view.findViewById(R.id.sign_up_password)).getText().toString();
            
            if (phone.isEmpty() || name.isEmpty() || password.isEmpty() ){
                Toast.makeText(getContext(), "Please fill all empty field!", Toast.LENGTH_SHORT).show();
            }
            if (!Utils.checkValidPatientName(name) ){
                Toast.makeText(getContext(), "Name is too long! Name is no longer than " + Utils.NAME_LENGTH_LIMIT + " characters!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(view.getContext(), OtpActivity.class);
                i.putExtra("phone", "+84" + phone);
                i.putExtra("password", password);
                i.putExtra("name", name);
                view.getContext().startActivity(i);
            }
        });
        return view;
    }
    public interface SignUpListener{
        void onBackToLogin();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof SignUpListener) {
            listener = (SignUpListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignUpListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}