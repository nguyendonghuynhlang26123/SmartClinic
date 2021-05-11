package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.activities.fragments.DoctorLoginFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.activities.fragments.NurseLoginFragment;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.RetrofitSingleton;
import com.team13.doctorclient.repositories.apis.PingApi;
import com.team13.doctorclient.repositories.services.HospitalService;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements DoctorLoginFragment.Listener,  NurseLoginFragment.Listener {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                String title = tab.getText().toString();
                switch (title.toLowerCase(Locale.US)){
                    case "doctor":
                        fragment = new DoctorLoginFragment();
                        loadFragment(fragment);
                        break;
                    case "nurse":
                        fragment = new NurseLoginFragment();
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
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void pingServer() {
        PingApi api = RetrofitSingleton.getInstance().create(PingApi.class);
        api.ping().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadFragment(new DoctorLoginFragment());
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

    private void getDataAndStartActivity(Class<?> cls){
        HospitalService hospitalService = new HospitalService();
        hospitalService.getHospital("6056b843cefabf3368f043cf", new OnSuccessResponse<HospitalModel>() {
            @Override
            public void onSuccess(HospitalModel hospital) {
                Store.get_instance().setHospital(hospital);

                if (Store.get_instance().isFullyLoaded()) {
                    Intent i = new Intent(LoginActivity.this, cls);
                    startActivity(i);
                }
            }
        });
    }
    @Override
    public void startDoctorActivity() {
        getDataAndStartActivity(HomeActivity.class);
    }

    @Override
    public void startNurseActivity() {
        getDataAndStartActivity(NurseHomeActivity.class);
    }
}