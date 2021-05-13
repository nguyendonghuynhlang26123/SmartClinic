package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.activities.fragments.DrugAddFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.activities.fragments.RescheduleFragment;
import com.team13.doctorclient.activities.fragments.ReviewPrescriptionFragment;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.DrugDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewPrescriptionActivity extends AppCompatActivity implements DrugAddFragment.AddDrugListener,
        RescheduleFragment.RescheduleListener, ReviewPrescriptionFragment.ReviewDrugListener {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    DrugAdapter drugAdapter;
    ArrayList<DrugDetail> addDrugArrayList=new ArrayList<>(5);

    Calendar myCalendar;
    Appointment appointment;

    TextInputEditText symptom, diagnostic;
    TextView  endDay, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);

        //Get Data
        Intent i = getIntent();
        appointment= (Appointment) i.getSerializableExtra("appointment");

        myCalendar= Calendar.getInstance();

        symptom = findViewById(R.id.input_symptom);
        diagnostic=findViewById(R.id.input_diagnostic);
        endDay=findViewById(R.id.prescription_patient_date_end);
        note=findViewById(R.id.note_prescription);
        MaterialToolbar topAppBar= findViewById(R.id.topAppBar);
        BottomAppBar bottomAppBar=findViewById(R.id.bottom_app_bar);
        RecyclerView drugList= findViewById(R.id.drug_list);

        drugAdapter= new DrugAdapter(this, true);
        drugList.setAdapter(drugAdapter);
        drugList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        ((TextView)findViewById(R.id.prescription_patient_name)).setText(appointment.getPatientName());
        ((TextView)findViewById(R.id.doctor_name)).setText("Dr." + Store.get_instance().getName());
        ((TextView) findViewById(R.id.prescription_patient_date_begin)).setText(appointment.getDate());

        symptom.setText(appointment.getNote());

        topAppBar.setNavigationOnClickListener(v -> finish());
        findViewById(R.id.pick_date).setOnClickListener(v -> handlePickDate());
        bottomAppBar.setOnMenuItemClickListener(item -> handleBottomBarEvent(item));
        bottomAppBar.setNavigationOnClickListener(v->{
            ReviewPrescriptionFragment reviewPrescriptionFragment =new ReviewPrescriptionFragment();
            reviewPrescriptionFragment.show(getSupportFragmentManager(),reviewPrescriptionFragment.getTag());
        });

        findViewById(R.id.add_drugBtn).setOnClickListener(v -> {
            DrugAddFragment drugAddFragment=new DrugAddFragment();
            drugAddFragment.show(getSupportFragmentManager(),drugAddFragment.getTag());
        });
    }

    private void handlePickDate() {
        DatePickerDialog dialog=new DatePickerDialog(NewPrescriptionActivity.this, (view, year, month, dayOfMonth) -> {
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
    }

    private boolean handleBottomBarEvent(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reschedule:
                try {
                    Date currentDate = format.parse(appointment.getDate() + " " + appointment.getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);

                    RescheduleFragment fragment = RescheduleFragment.newInstance(getTempReschedule(calendar));
                    fragment.show(getSupportFragmentManager(),fragment.getTag());
                } catch (ParseException exception){
                    return false;
                }
                return true;
            case R.id.finish:
                //TODO
                return false;
        }
        return false;
    }

    public String getTempReschedule(Calendar calendar){
        // ***TODO
        calendar.add(Calendar.WEEK_OF_MONTH,1);
        return format.format(calendar.getTime());
//        return calendar.toString();
    }
    @Override
    public void onSaveDrug(DrugDetail addDrug) {
        addDrugArrayList.add(addDrug);
        Log.w("List", addDrugArrayList.get(0).getNote());
        drugAdapter.setData(addDrugArrayList);
    }
    @Override
    public void onSaveListDrug(ArrayList<DrugDetail> addDrugArrayList){
        this.addDrugArrayList.addAll(addDrugArrayList);
        Log.w("List", addDrugArrayList.get(0).getNote());
        drugAdapter.setData(this.addDrugArrayList);
    }
    @Override
    public void setReschedule(Appointment appointment) {
        //***TODO: Send to db
        endDay.setText(appointment.getDate());
    }
    private void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(fragment);
        transaction.remove(fragment);
        transaction.commit();
    }
}