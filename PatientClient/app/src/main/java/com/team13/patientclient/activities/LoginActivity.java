package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.LoginFragment;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.SignUpFragment;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.models.HospitalModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.PingApi;
import com.team13.patientclient.repository.apis.ServicePackApi;
import com.team13.patientclient.repository.services.HospitalService;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Listener, SignUpFragment.SignUpListener{
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                String title = Objects.requireNonNull(tab.getText()).toString();
                switch (title.toLowerCase(Locale.US)){
                    case "login":
                        fragment = new LoginFragment();
                        loadFragment(fragment);
                        break;
                    case "signup":
                        fragment = new SignUpFragment();
                        loadFragment(fragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pingServer();
    }

    private void pingServer() {
        PingApi api = RetrofitSingleton.getInstance().create(PingApi.class);
        api.ping().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadFragment(new LoginFragment());
                View card = findViewById(R.id.action_card);
                Animation slideUp = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.slide_up);
                findViewById(R.id.app_icon).startAnimation(slideUp);
                findViewById(R.id.welcome_label).startAnimation(slideUp);

                card.setVisibility(View.VISIBLE);
                card.startAnimation(slideUp);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                View layout = findViewById(R.id.login_view);
                Snackbar snackbar = Snackbar
                        .make(layout, "Cannot access to Server! Please try again.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try again", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pingServer();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void setLoginFragment(){
        loadFragment(new LoginFragment());
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }
    public void setSignUpFragment(){
        loadFragment(new SignUpFragment());
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }

    @Override
    public void startProgram() {
        //INIT DATA
        getHospitalData();
    }

    public void switchActivity(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void getHospitalData(){
        HospitalService hospitalService = new HospitalService();
        hospitalService.getHospital("6056b843cefabf3368f043cf", new OnSuccessResponse<HospitalModel>() {
            @Override
            public void onSuccess(HospitalModel hospital) {
                Store.get_instance().setHospital(hospital);

                if (Store.get_instance().isFullyLoaded()) switchActivity();
            }
        });
    }

    @Override
    public void onBackToLogin() {
        setLoginFragment();
    }
}