package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.MaterialToolbar;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.activities.fragments.ServiceDisplayFragment;
import com.team13.patientclient.models.ServicePack;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.ServicePackService;
import java.util.ArrayList;
import java.util.Arrays;

public class ServiceActivity extends AppCompatActivity implements ServiceDisplayFragment.ServiceDisplayListener {
    ArrayList<ServicePack> data;
    String searchQuery="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        SearchView searchView = findViewById(R.id.service_search);
        topAppBar.setNavigationOnClickListener(v-> finish());
        Intent i = getIntent();
        if(Intent.ACTION_SEARCH.equals(i.getAction())){
            searchQuery = i.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(searchQuery, false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                callApiAndRender();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //rendering dumb data while waiting for response from server
//        ServicePackAdapter servicePackAdapter = new ServicePackAdapter(this, getEmptyModels(10));
//        serviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        serviceList.setAdapter(servicePackAdapter);

        callApiAndRender();
    }

    private void callApiAndRender() {
        ServicePackService service = new ServicePackService();
        loadFragment(new ProgressFragment());
        if(searchQuery.isEmpty()){
            service.get(new OnSuccessResponse<ServicePack[]>() {
                @Override
                public void onSuccess(ServicePack[] list) {
                    //Rendering data as soon as it received
                    data = new ArrayList<>(Arrays.asList(list));
                    loadFragment(new ServiceDisplayFragment());
                }
            });
        } else {
            service.get(searchQuery, new OnSuccessResponse<ServicePack[]>() {
                @Override
                public void onSuccess(ServicePack[] list) {
                    //Rendering data as soon as it received
                    data = new ArrayList<>(Arrays.asList(list));
                    loadFragment(new ServiceDisplayFragment());
                }
            });
        }

    }

    @Override
    public ArrayList<ServicePack> getDisplayService() {
        return data;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}