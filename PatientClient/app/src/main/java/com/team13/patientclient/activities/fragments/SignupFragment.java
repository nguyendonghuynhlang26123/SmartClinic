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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText phoneInput;
    Button loginButton;
    SignUpListener listener;
    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        phoneInput = view.findViewById(R.id.sign_up_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        loginButton = view.findViewById(R.id.login_text_button);
        loginButton.setOnClickListener(v -> listener.onBackToLogin());

        view.findViewById(R.id.sign_up_button).setOnClickListener(v ->{
            String phone = Utils.unFormatPhoneNumber(phoneInput.getText().toString());
            String name = ((TextView) view.findViewById(R.id.sign_up_name)).getText().toString();
            String password = ((TextView) view.findViewById(R.id.sign_up_password)).getText().toString();
            
            if (phone.isEmpty() || name.isEmpty() || password.isEmpty() ){
                Toast.makeText(getContext(), "Please fill all empty field!", Toast.LENGTH_SHORT).show();
            }
            if (!Utils.checkValidPatientName(name) ){
                Toast.makeText(getContext(), "Name is too long! Name is no longer than " + Utils.NAME_LENGTH_LIMIT + " characters!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(view.getContext(), OtpActivity.class);
                i.putExtra("phone", phone);
                view.getContext().startActivity(i);
            }

//            AuthService authService = new AuthService();
//            authService.register(phone, password, name, new OnSuccessResponse<AccountModel>() {
//                @Override
//                public void onSuccess(AccountModel account) {
//                    Store.get_instance().setUserAccount(account);
//
//                    Intent i = new Intent(view.getContext(), MainActivity.class);
//                    startActivity(i);
//                }
//            });
        });
        return view;
    }
    public interface SignUpListener{
        void onBackToLogin();
    }

    @Override
    public void onAttach(Context context) {
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