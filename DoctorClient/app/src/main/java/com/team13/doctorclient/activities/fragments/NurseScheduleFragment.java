package com.team13.doctorclient.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.NurseHomeActivity;
import com.team13.doctorclient.activities.PatientDetailActivity;
import com.team13.doctorclient.activities.QRReader;
import com.team13.doctorclient.adapters.DoctorChooserAdapter;
import com.team13.doctorclient.adapters.PendingAppointmentAdapter;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.Doctor;
import com.team13.doctorclient.models.HospitalModel;
import com.team13.doctorclient.models.ScheduleItem;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;
import com.team13.doctorclient.repositories.services.DoctorService;
import com.team13.doctorclient.repositories.services.PatientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class NurseScheduleFragment extends Fragment {
    View view;
    PendingAppointmentAdapter adapter;
    NurseScheduleListener listener;
    Spinner doctorChooser;

    String today;

    public NurseScheduleFragment() {
        // Required empty public constructor
    }
    public static NurseScheduleFragment newInstance(String param1, String param2) {
        NurseScheduleFragment fragment = new NurseScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_nurse_schedule, container, false);
        TextView currentDay= view.findViewById(R.id.current_day);
        currentDay.setText(Utils.getCurrentDateString());

        //Setup Recycle view
        RecyclerView timeline= view.findViewById(R.id.schedule);
        adapter = new PendingAppointmentAdapter(getContext());

        //TODO: show a input dialog then pass phoneNumber to verifyPhoneAndProcess()
        adapter.setListener((appointment) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle("Check-in");
            View input = LayoutInflater.from(view.getContext()).inflate(R.layout.nurse_checkin_phone_input, null);
            alert.setView(input);
            TextInputEditText phoneInput = input.findViewById(R.id.input_phone);
            alert.setPositiveButton("Ok", (dialog, whichButton) -> verifyPhoneAndProcess(Objects.requireNonNull(phoneInput.getText()).toString(), appointment));
            alert.show();

        });

        timeline.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        timeline.setAdapter(adapter);

        if (Utils.DEBUG_MODE){
            today = Utils.DEBUG_DATE;
        }
        else today = Utils.getCurrentDateString();

        view.findViewById(R.id.logout_button).setOnClickListener(v->{
            listener.onLogout();
        });

        doctorChooser = view.findViewById(R.id.doctor_chooser);
        DoctorChooserAdapter adapter1 = new DoctorChooserAdapter(view.getContext());
        doctorChooser.setAdapter(adapter1);
        doctorChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                renderData(adapter1.getItem(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        prepareDoctorList(adapter1);
        return view;
    }

    private void verifyPhoneAndProcess(String phoneNumber, Appointment appointment) {
        PatientService service = new PatientService();
        service.getByPhone(phoneNumber, new OnSuccessResponse<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> response) {
                String responseId = response.get("user_infor");
                if (appointment.getPatientId().equals(responseId)){
                    Toast.makeText(getContext(), "Info Matched" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), PatientDetailActivity.class);
                    intent.putExtra("appointment", appointment);
                    intent.putExtra("mode", Utils.PATIENTDETAIL_CHECKIN_MODE);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getContext(), "Wrong user" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void prepareDoctorList(DoctorChooserAdapter adapter){
        DoctorService service = new DoctorService();
        service.getDoctors(new OnSuccessResponse<Doctor[]>() {
            @Override
            public void onSuccess(Doctor[] response) {
                adapter.setData(new ArrayList<>(Arrays.asList(response)));
                renderData(response[0].getId());
            }
        });
    }

    void renderData(String doctorId){
        //Get list of possible time
        HospitalModel hospital = Store.get_instance().getHospital();
        final ArrayList<String> timeLines;
        if (Utils.DEBUG_MODE){
            timeLines = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );
        }
        else timeLines = Utils.getAvailableTime(Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 ));

        //Render the timeline view
        AppointmentService service = new AppointmentService();
        String[] statuses = {Utils.STATUS_PENDING};
        service.getAppointmentByDate(doctorId,today, statuses, new OnSuccessResponse<Appointment[]>() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.QRSCAN_RESULT_INTENT){
            if (resultCode == Activity.RESULT_OK){
                String appointmentId = data.getStringExtra("appointment");
                adapter.updateStatus(appointmentId, Utils.STATUS_PROCESSING);
            }
        }
    }

    public interface NurseScheduleListener{
        void onLogout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NurseScheduleListener) {
            listener= (NurseScheduleListener) context;
        }
        else {
//            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener= null;
    }

}