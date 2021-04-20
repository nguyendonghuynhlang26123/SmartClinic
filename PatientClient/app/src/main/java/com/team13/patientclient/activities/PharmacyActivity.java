package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.CategoryFragment;
import com.team13.patientclient.activities.fragments.DrugsDisplayFragment;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.models.Category;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.CategoryService;
import com.team13.patientclient.repository.services.DrugService;

import java.util.ArrayList;
import java.util.Arrays;

public class PharmacyActivity extends AppCompatActivity implements DrugsDisplayFragment.DrugDisplayListener {

    ArrayList<Category> drugCategories;
    MaterialToolbar topAppBar;
    String searchQuery="";
    FragmentManager fragmentManager = getSupportFragmentManager();
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            Toast.makeText(this,String.valueOf(fragmentManager.getBackStackEntryCount()),Toast.LENGTH_LONG).show();
            if(fragmentManager.getBackStackEntryCount()<=2){
                finish();
            } else {
                fragmentManager.popBackStack();
            }
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.cart) {
                Intent i = new Intent(this, Cart.class);
                startActivity(i);
                return true;
            }
            return false;
        });
        SearchView searchView = findViewById(R.id.category_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                renderDrugCategories();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        renderDrugCategories();
    }


    @Override
    public ArrayList<Category> getDisplayData() {
        return drugCategories;
    }

    @Override
    public void categoryDetailLoad(String categoryId) {
        renderCategoryItems(categoryId);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void renderDrugCategories(){
        CategoryService service = new CategoryService();
        loadFragment(new ProgressFragment());
        if(searchQuery.isEmpty()){
            service.getCategoryList(new OnSuccessResponse<Category[]>() {
                @SuppressLint("NewApi")
                @Override
                public void onSuccess(Category[] list) {
                    drugCategories = new ArrayList<>(Arrays.asList(list));
                    Fragment fragment = new DrugsDisplayFragment();
                    loadFragment(fragment);
                }
            });
        } else {
            // Get with @Query
            // ***TODO
        }

    }
    private void renderCategoryItems(String categoryId){
        // ***TODO: GET DRUG LIST OF A CATEGORY
        // Connect api and get data with @categoryName
        DrugService service = new DrugService();
        service.getDrugByCategory(categoryId, new OnSuccessResponse<DrugModel[]>() {
            @Override
            public void onSuccess(DrugModel[] list) {
                Fragment fragment = CategoryFragment.newInstance(new ArrayList<>(Arrays.asList(list)));
                loadFragment(fragment);
            }
        });

    }
}