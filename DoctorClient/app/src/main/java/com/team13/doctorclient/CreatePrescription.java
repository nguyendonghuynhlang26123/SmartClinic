package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Drug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreatePrescription extends AppCompatActivity implements DrugAddFragment.AddDrugListener {
    MaterialToolbar topAppBar;
    BottomAppBar bottomAppBar;
    FloatingActionButton addDrug;
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    ArrayList<Drug> addDrugArrayList=new ArrayList<>(5);
    DrugAddFragment drugAddFragment;
    Calendar myCalendar;
    DatePickerDialog dialog;
    TextView startDay, endDay;
    ImageView pickDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);
        topAppBar= findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        bottomAppBar=findViewById(R.id.bottom_app_bar);
        startDay= findViewById(R.id.prescription_patient_date_begin);
        endDay=findViewById(R.id.prescription_patient_date_end);
        myCalendar= Calendar.getInstance();
        pickDate=findViewById(R.id.pick_date);
        pickDate.setOnClickListener(v -> {
            DatePickerDialog dialog=new DatePickerDialog(CreatePrescription.this, (view, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                endDay.setText(""+dayOfMonth+"/"+month+"/"+year);
                Log.d("date", ""+dayOfMonth+"/"+(month+1)+"/"+year);
            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
//                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                endDay.setText(""+myCalendar.get(Calendar.DAY_OF_MONTH));
//                            }
//                        });
        });
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.reschedule:

                        return true;
                    case R.id.finish:
                        //TODO
                        return true;
                }

                return false;
            }
        });
        addDrug= findViewById(R.id.add_drugBtn);
        addDrug.setOnClickListener(v -> {
            drugAddFragment=new DrugAddFragment();
            assert getSupportFragmentManager()!=null;
            drugAddFragment.show(getSupportFragmentManager(),drugAddFragment.getTag());
        });
        drugList= findViewById(R.id.drug_list);
        drugAdapter= new DrugAdapter(this);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


    }


    @Override
    public void onSaveDrug(Drug addDrug) {
        addDrugArrayList.add(addDrug);
        drugAdapter.setData(addDrugArrayList);
        drugAddFragment.dismiss();
    }

}