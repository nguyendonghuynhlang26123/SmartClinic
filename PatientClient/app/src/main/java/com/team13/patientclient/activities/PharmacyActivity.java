package com.team13.patientclient.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.DrugsDisplayFragment;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.Category;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnResponse;
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
        renderingMedicineList();
    }

    private void renderingMedicineList() {
        drugService = new DrugService();
        loadFragment(new ProgressFragment());
        // Fix data later //
        drugService.getMinimizedData(new OnResponse<DrugModel[]>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onRequestSuccess(DrugModel[] list) {
            drugCategories = getEmptyCategory();
            drugCategories.forEach(category -> {
                category.setDrugList(list);
            });
            Fragment fragment = new DrugsDisplayFragment();
            loadFragment(fragment);
            }
        });

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

    private ArrayList<Category> getEmptyCategory(){
        ArrayList<Category> categories = new ArrayList<>(3);
        categories.add(new Category("Category 1"));
        categories.add(new Category("Category 2"));
        categories.add(new Category("Category 3"));
        return categories;
    }
}