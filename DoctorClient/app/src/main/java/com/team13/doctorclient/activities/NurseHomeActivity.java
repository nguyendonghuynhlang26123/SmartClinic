package com.team13.doctorclient.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.PendingAppointmentAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.util.ArrayList;
import java.util.Arrays;

public class NurseHomeActivity extends AppCompatActivity {
    PendingAppointmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        TextView currentDay= findViewById(R.id.current_day);
        currentDay.setText(Utils.getCurrentDateString());

        RecyclerView timeline= findViewById(R.id.schedule);
        adapter = new PendingAppointmentAdapter(NurseHomeActivity.this);
        adapter.setListener(new PendingAppointmentAdapter.Listener() {
            @Override
            public void onAccept(String appointmentId, String patientId) {
                Toast.makeText(NurseHomeActivity.this, appointmentId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NurseHomeActivity.this, QRReader.class);
                intent.putExtra("appointment", appointmentId);
                intent.putExtra("patient", patientId);
                startActivityForResult(intent, Utils.QRSCAN_RESULT_INTENT);
            }
        });
        timeline.setLayoutManager(new LinearLayoutManager(NurseHomeActivity.this,LinearLayoutManager.VERTICAL,false));
        timeline.setAdapter(adapter);

        //renderData(Utils.getCurrentDateString());
        renderData("12/05/2021");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.QRSCAN_RESULT_INTENT){
            if (resultCode == Activity.RESULT_OK){
                String appointmentId = data.getStringExtra("appointment");
                adapter.updateStatus(appointmentId, Utils.STATUS_PROCESSING);
            }
        }
    }

    void renderData(String date){
        //Get list of possible time
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> timeLines = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );

        //Render the timeline view
        AppointmentService service = new AppointmentService();
        service.getAppointmentByDate(date, new OnSuccessResponse<Appointment[]>() {
            @Override
            public void onSuccess(Appointment[] response) {
                ArrayList<Appointment> appointments = new ArrayList<>(Arrays.asList(response));
                adapter.setData(getScheduleItemsFromAppointments(timeLines,appointments));
            }
        });

    }

    ArrayList<ScheduleItem> getScheduleItemsFromAppointments(ArrayList<String> timeLines, ArrayList<Appointment> appointments ){
        ArrayList<ScheduleItem> result = new ArrayList<>();
        for (String time: timeLines)
            result.add(new ScheduleItem(null,time));

        for (Appointment appointment: appointments){
            int index = timeLines.indexOf(appointment.getTime());
            if (index != -1) result.get(index).setAppointment(appointment);
        }

        return result;
    }
}