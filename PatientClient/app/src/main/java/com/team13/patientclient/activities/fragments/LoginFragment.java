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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.MainActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.LoginActivity;
import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.repository.services.AuthService;

import org.json.JSONObject;

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
    TextInputEditText passwordInput;
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
        passwordInput = view.findViewById(R.id.input_password);
        phoneInput = view.findViewById(R.id.input_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        signUpButton = view.findViewById(R.id.sign_up_text_button);
        signUpButton.setOnClickListener(v -> {
            ((LoginActivity)getActivity()).setSignUpFragment();
        });
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString().replaceAll("[^\\d]","");
            String password = passwordInput.getText().toString();
            if (phone.isEmpty() || password.isEmpty())
                Toast.makeText(getContext(), "Empty Input! Please try again", Toast.LENGTH_SHORT).show();
            verifyAndProcess(phone, password, view);
        });

        return view;
    }

    private void verifyAndProcess(String phone, String password, View view) {
        AuthService auth = new AuthService();
        auth.login("+84" + phone, password).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.isSuccessful()){
                    AccountModel account = response.body();
                    Store.get_instance().setUserAccount(account);
                    Log.d("LONG", new Gson().toJson(account));

                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    startActivity(i);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {

            }
        });
    }

}