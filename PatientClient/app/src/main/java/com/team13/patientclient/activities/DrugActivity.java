package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.fragments.ProgressFragment;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.DrugService;

public class DrugActivity extends AppCompatActivity {
    MaterialToolbar topAppBar;
    DrugModel medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        String id = getIntent().getStringExtra("id");
        Log.d("LONG", "ID: " + id);

        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        setExpand(R.id.description_expand, R.id.description_collapse, R.id.description_content);
        setExpand(R.id.ingredient_expand, R.id.ingredient_collapse, R.id.ingredient_content);
        setExpand(R.id.manual_expand, R.id.manual_collapse, R.id.manual_content);
        setExpand(R.id.preservation_expand, R.id.preservation_collapse, R.id.preservation_content);
        renderingData(id);
    }

    private void renderingData(String id){
        DrugService service = new DrugService();
        Fragment fragment = new ProgressFragment();
        loadFragment(fragment);
        service.getDrugById(id, new OnSuccessResponse<DrugModel>() {
            @Override
            public void onSuccess(DrugModel drug) {
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
    @SuppressLint("NonConstantResourceId")
    private void setExpand(int expandId, int collapseId, int contentId){
        ImageButton buttonExpand = findViewById(expandId);
        buttonExpand.setOnClickListener(v->{
            TextView contentView = findViewById(contentId);
            switch (expandId){
                case R.id.description_expand:
                    contentView.setText(medicine.getDescription());
                    break;
                case R.id.ingredient_expand:
                    contentView.setText(medicine.getIngredients());
                    break;
                case R.id.manual_expand:
                    contentView.setText(medicine.getUserManual());
                    break;
                case R.id.preservation_expand:
                    contentView.setText(medicine.getPreservation());
                    break;
            }

            contentView.setVisibility(View.VISIBLE);
            buttonExpand.setVisibility(View.GONE);
            ImageButton buttonCollapse = findViewById(collapseId);
            buttonCollapse.setVisibility(View.VISIBLE);
            buttonCollapse.setOnClickListener(l->{
                contentView.setVisibility(View.GONE);
                buttonCollapse.setVisibility(View.GONE);
                buttonExpand.setVisibility(View.VISIBLE);
            });
        });
    }
}