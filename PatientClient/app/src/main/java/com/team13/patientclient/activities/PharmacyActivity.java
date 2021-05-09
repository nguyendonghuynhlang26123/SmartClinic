package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.fragments.DrugListFragment;
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
    TextView searchResult;
    MaterialCardView banner;
    String searchQuery="";
    DrugService drugService;
    FragmentManager fragmentManager = getSupportFragmentManager();
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        drugService = new DrugService();

        searchResult = findViewById(R.id.search_result);
        banner = findViewById(R.id.pharmacy_banner);

        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            if(fragmentManager.getBackStackEntryCount()<=2){
                finish();
            } else {
                banner.setVisibility(View.VISIBLE);
                searchResult.setVisibility(View.GONE);
                renderDrugCategories();
            }
        });
        SearchView searchView = findViewById(R.id.category_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                banner.setVisibility(View.GONE);
                loadFragment(new ProgressFragment());
                renderDrugsListByKey(query);
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

    private void renderDrugsListByKey(String key){
        drugService.searchDrug(key, 10, new OnSuccessResponse<DrugModel[]>() {
            @Override
            public void onSuccess(DrugModel[] list) {
                searchResult.setVisibility(View.VISIBLE);
                if (list.length != 0) searchResult.setText("Found "+list.length+" result(s) matching \"" + key +"\":");
                else searchResult.setText("Sorry, but nothing matched your search term. Please try again!");
                Fragment fragment = DrugListFragment.newInstance(new ArrayList<>(Arrays.asList(list)));
                loadFragment(fragment);
            }
        });
    }

    private void renderDrugCategories(){
        CategoryService categoryService = new CategoryService();
        loadFragment(new ProgressFragment());
        categoryService.getCategoryList(new OnSuccessResponse<Category[]>() {
            @SuppressLint("NewApi")
            @Override
            public void onSuccess(Category[] list) {
                drugCategories = new ArrayList<>(Arrays.asList(list));
                Fragment fragment = new DrugsDisplayFragment();
                loadFragment(fragment);
            }
        });

    }
    private void renderCategoryItems(String categoryId){
        // ***TODO: GET DRUG LIST OF A CATEGORY
        // Connect api and get data with @categoryName
        drugService.getDrugByCategory(categoryId, new OnSuccessResponse<DrugModel[]>() {
            @Override
            public void onSuccess(DrugModel[] list) {
                Fragment fragment = DrugListFragment.newInstance(new ArrayList<>(Arrays.asList(list)));
                loadFragment(fragment);
            }
        });

    }
}