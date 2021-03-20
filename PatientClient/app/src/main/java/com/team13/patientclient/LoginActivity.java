package com.team13.patientclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ProgressBar progressBar;
    TextView welcomeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progress_circular_indicator);
        welcomeData = findViewById(R.id.welcome_data);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                String title = tab.getText().toString();
                switch (title.toLowerCase(Locale.US)){
                    case "login":
                        fragment = new LoginFragment();
                        loadFragment(fragment);
                        break;
                    case "signup":
                        fragment = new SignupFragment();
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
        loadFragment(new LoginFragment());
        Test();
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
        loadFragment(new SignupFragment());
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }
    private void Test(){
        URL api_path = NetworkUtils.buildUrlTest();
        new ConnectServer().execute(api_path);
    }
    public class ConnectServer extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL api_path = urls[0];
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(api_path);
            } catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            if(s!=null && !s.equals("")){
                try {
                    JSONObject result = new JSONObject(s);
                    String data = result.getString("name");
                    welcomeData.setText(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                welcomeData.setVisibility(View.VISIBLE);
            }
        }
    }
}