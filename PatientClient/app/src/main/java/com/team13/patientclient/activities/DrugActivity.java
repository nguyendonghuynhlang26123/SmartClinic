package com.team13.patientclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnResponse;
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
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });

        renderingData(id);
    }

    private void renderingData(String id){
        DrugService service = new DrugService();
        service.getDrugById(id, new OnResponse<DrugModel>() {
            @Override
            public void onRequestSuccess(DrugModel drug) {
                ((TextView) findViewById(R.id.drug_name)).setText(drug.getName());
                ((TextView) findViewById(R.id.drug_brand)).setText(drug.getBrand());
                ((TextView) findViewById(R.id.drug_from)).setText(drug.getFromCountry());
                ((TextView) findViewById(R.id.drug_volume)).setText(drug.getVolume());
                ((TextView) findViewById(R.id.drug_price)).setText(Utils.formatingPrice(drug.getPrice()));

                if (!drug.getThumbnail().isEmpty()) Picasso.get().load(drug.getThumbnail()).into((ImageView) findViewById(R.id.drug_thumbnail));
            }
        });
    }
}