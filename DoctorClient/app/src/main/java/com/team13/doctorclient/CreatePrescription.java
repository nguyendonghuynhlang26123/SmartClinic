package com.team13.doctorclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
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
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.DoctorTimeline;
import com.team13.doctorclient.models.Drug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreatePrescription extends AppCompatActivity implements DrugAddFragment.AddDrugListener,
        RescheduleFragment.RescheduleListener {
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
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String time;
    RescheduleFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);
        Intent i = getIntent();
        time = i.getStringExtra("time");
        TextView patientName = findViewById(R.id.prescription_patient_name);
        patientName.setText(i.getStringExtra("patientName"));
        topAppBar= findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        bottomAppBar=findViewById(R.id.bottom_app_bar);
        startDay= findViewById(R.id.prescription_patient_date_begin);
        startDay.setText(i.getStringExtra("time").split(" ")[0]);
        endDay=findViewById(R.id.prescription_patient_date_end);
        myCalendar= Calendar.getInstance();
        pickDate=findViewById(R.id.pick_date);
        pickDate.setOnClickListener(v -> {
            DatePickerDialog dialog=new DatePickerDialog(CreatePrescription.this, (view, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                endDay.setText(""+dayOfMonth+"/"+(month+1)+"/"+year);
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.reschedule:
                        try {
                            Date date = format.parse(time);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            fragment = RescheduleFragment.newInstance(getTempReschedule(calendar));
                            fragment.show(getSupportFragmentManager(),fragment.getTag());
                        } catch (ParseException exception){

                        }
                        return true;
                    case R.id.finish:
                        //TODO
                        return true;
                }

                return false;
            }
        });
        bottomAppBar.setNavigationOnClickListener(v->{
            ReviewPrescriptionFragment reviewPrescriptionFragment=new ReviewPrescriptionFragment();
            assert  getSupportFragmentManager()!=null;
            reviewPrescriptionFragment.show(getSupportFragmentManager(),reviewPrescriptionFragment.getTag());
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

    public String getTempReschedule(Calendar calendar){
        // ***TODO
        calendar.add(Calendar.WEEK_OF_MONTH,1);
        return format.format(calendar.getTime());
//        return calendar.toString();
    }
    @Override
    public void onSaveDrug(Drug addDrug) {
        addDrugArrayList.add(addDrug);
        drugAdapter.setData(addDrugArrayList);
        drugAddFragment.dismiss();
    }

    @Override
    public void setReschedule(Appointment appointment) {
        //***TODO: Send to db

        endDay.setText(appointment.getDate());
        removeFragment(fragment);
    }
    private void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(fragment);
        transaction.remove(fragment);
        transaction.commit();
    }
}