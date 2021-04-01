package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.DrugService;

public class DrugActivity extends AppCompatActivity {
    MaterialToolbar topAppBar;
    DrugModel medicine;
    ImageButton descriptionExpand;
    ImageButton ingredientExpand;
    ImageButton manualExpand;
    ImageButton preserveExpand;
    ImageButton expanded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        String id = getIntent().getStringExtra("id");
        Log.d("LONG", "ID: " + id);

        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        descriptionExpand = findViewById(R.id.description_expand);
        descriptionExpand.setOnClickListener(v->{
            TextView description = findViewById(R.id.description_content);
            description.setText(medicine.getDescription());
            description.setVisibility(View.VISIBLE);
            descriptionExpand.setVisibility(View.GONE);
            expanded = findViewById(R.id.description_expanded);
            expanded.setVisibility(View.VISIBLE);
            expanded.setOnClickListener(l->{
                description.setVisibility(View.GONE);
                expanded.setVisibility(View.GONE);
                descriptionExpand.setVisibility(View.VISIBLE);
            });
        });
        manualExpand = findViewById(R.id.manual_expand);
        manualExpand.setOnClickListener(v->{
            TextView manual = findViewById(R.id.manual_content);
            manual.setText(medicine.getUserManual());
            manual.setVisibility(View.VISIBLE);
            manualExpand.setVisibility(View.GONE);
            expanded = findViewById(R.id.manual_expanded);
            expanded.setVisibility(View.VISIBLE);
            expanded.setOnClickListener(l->{
                manual.setVisibility(View.GONE);
                expanded.setVisibility(View.GONE);
                manualExpand.setVisibility(View.VISIBLE);
            });
        });
        ingredientExpand = findViewById(R.id.ingredient_expand);
        ingredientExpand.setOnClickListener(v->{
            TextView ingredient = findViewById(R.id.ingredient_content);
            ingredient.setText(medicine.getIngredients());
            ingredient.setVisibility(View.VISIBLE);
            ingredientExpand.setVisibility(View.GONE);
            expanded = findViewById(R.id.ingredient_expanded);
            expanded.setVisibility(View.VISIBLE);
            expanded.setOnClickListener(l->{
                ingredient.setVisibility(View.GONE);
                expanded.setVisibility(View.GONE);
                ingredientExpand.setVisibility(View.VISIBLE);
            });
        });
        preserveExpand = findViewById(R.id.preservation_expand);
        preserveExpand.setOnClickListener(v->{
            TextView preservation = findViewById(R.id.preservation_content);
            preservation.setText(medicine.getPreservation());
            preservation.setVisibility(View.VISIBLE);
            preserveExpand.setVisibility(View.GONE);
            expanded = findViewById(R.id.preservation_expanded);
            expanded.setVisibility(View.VISIBLE);
            expanded.setOnClickListener(l->{
                preservation.setVisibility(View.GONE);
                expanded.setVisibility(View.GONE);
                preserveExpand.setVisibility(View.VISIBLE);
            });
        });
        renderingData(id);
    }

    private void renderingData(String id){
        DrugService service = new DrugService();
        Fragment fragment = new ProgressFragment();
        loadFragment(fragment);
        service.getDrugById(id, new OnResponse<DrugModel>() {
            @Override
            public void onRequestSuccess(DrugModel drug) {
                medicine = drug;
                ((TextView) findViewById(R.id.drug_name)).setText(drug.getName());
                ((TextView) findViewById(R.id.drug_brand)).setText(drug.getBrand());
                ((TextView) findViewById(R.id.drug_from)).setText(drug.getFromCountry());
                ((TextView) findViewById(R.id.drug_volume)).setText(drug.getVolume());
                ((TextView) findViewById(R.id.drug_price)).setText(Utils.formatingPrice(drug.getPrice()));

                if (!drug.getThumbnail().isEmpty()) Picasso.get().load(drug.getThumbnail()).into((ImageView) findViewById(R.id.drug_thumbnail));
                removeFragment(fragment);
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
    private void removeFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(fragment);
        transaction.remove(fragment);
        transaction.commit();
    }
}