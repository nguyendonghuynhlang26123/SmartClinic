package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.DrugsDisplayFragment;
import com.team13.patientclient.models.Category;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.CategoryService;
import com.team13.patientclient.repository.services.DrugService;

import java.util.ArrayList;
import java.util.Arrays;

public class PharmacyActivity extends AppCompatActivity implements DrugsDisplayFragment.DrugDisplayListener {
    DrugService drugService;
    ArrayList<DrugModel> data;
    ArrayList<Category> drugCategories;
    MaterialToolbar topAppBar;
    CircularProgressIndicator progressIndicator;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.cart) {
                Intent i = new Intent(this, Cart.class);
                startActivity(i);
                return true;
            }
            return false;
        });
        renderDrugCategories();
    }


    @Override
    public ArrayList<Category> getDisplayData() {
        return drugCategories;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void renderDrugCategories(){
        CategoryService service = new CategoryService();
        service.getCategoryList(new OnSuccessResponse<Category[]>() {
            @SuppressLint("NewApi")
            @Override
            public void onSuccess(Category[] list) {
                drugCategories = new ArrayList<>(Arrays.asList(list));

                Fragment fragment = new DrugsDisplayFragment();
                loadFragment(fragment);
            }
        });
    }
}