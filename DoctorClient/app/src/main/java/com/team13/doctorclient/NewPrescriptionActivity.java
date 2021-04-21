package com.team13.doctorclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.team13.doctorclient.adapters.DrugAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.Drug;
import com.team13.doctorclient.models.Prescription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewPrescriptionActivity extends AppCompatActivity implements DrugAddFragment.AddDrugListener,
        RescheduleFragment.RescheduleListener,ReviewPrescriptionFragment.ReviewDrugListener {
    MaterialToolbar topAppBar;
    BottomAppBar bottomAppBar;
    FloatingActionButton addDrug;
    RecyclerView drugList;
    DrugAdapter drugAdapter;
    ArrayList<Drug> addDrugArrayList=new ArrayList<>(5);
    DrugAddFragment drugAddFragment;
    Calendar myCalendar;
    DatePickerDialog dialog;
    TextView startDay, endDay, note;
    ImageView pickDate;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String time, date;
    RescheduleFragment fragment;
    Appointment appointment;
    TextInputEditText symptom, diagnostic;
    ReviewPrescriptionFragment reviewPrescriptionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);
        Intent i = getIntent();
        appointment= (Appointment) i.getSerializableExtra("appointment");

        time=appointment.getTime();
        date = appointment.getDate();

        TextView patientName = findViewById(R.id.prescription_patient_name);
        patientName.setText(appointment.getPatientId());

        symptom=findViewById(R.id.input_symptom);
        symptom.setText(appointment.getNote());

        diagnostic=findViewById(R.id.input_diagnostic);

        note=findViewById(R.id.note_prescription);
        topAppBar= findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        bottomAppBar=findViewById(R.id.bottom_app_bar);
        startDay= findViewById(R.id.prescription_patient_date_begin);
        startDay.setText(appointment.getDate());
        endDay=findViewById(R.id.prescription_patient_date_end);
        myCalendar= Calendar.getInstance();
        pickDate=findViewById(R.id.pick_date);
        pickDate.setOnClickListener(v -> {
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
        });
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.reschedule:
                    try {
                        Date currentDate = format.parse(date + " " + time);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(currentDate);
                        fragment = RescheduleFragment.newInstance(getTempReschedule(calendar));
                        fragment.show(getSupportFragmentManager(),fragment.getTag());
                    } catch (ParseException exception){
                        return false;
                    }
                    return true;
                case R.id.finish:
                    //TODO
                    return true;
            }

            return false;
        });
        bottomAppBar.setNavigationOnClickListener(v->{
            reviewPrescriptionFragment =new ReviewPrescriptionFragment();
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
    public void onSaveListDrug(ArrayList<Drug> addDrugArrayList){
        this.addDrugArrayList=addDrugArrayList;
        drugAdapter.setData(addDrugArrayList);
        reviewPrescriptionFragment.dismiss();
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
    private Prescription getPrescription(){
        // id??
        return new Prescription("001",appointment.getPatientId(),addDrugArrayList,note.getText().toString(),symptom.getText().toString(),diagnostic.getText().toString(),startDay.getText().toString(),endDay.getText().toString());
    }
}