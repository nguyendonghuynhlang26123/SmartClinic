package com.team13.patientclient.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.TreatmentAdapter;
import com.team13.patientclient.models.Appointment;
import com.team13.patientclient.models.Prescription;
import com.team13.patientclient.models.Treatment;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.AppointmentService;
import com.team13.patientclient.repository.services.PatientService;
import java.util.ArrayList;
import java.util.Arrays;


public class AppointmentFragment extends Fragment {
    ArrayList <Treatment> treatments = new ArrayList<>();
    public AppointmentFragment() {
        // Required empty public constructor
    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        TextView notify = view.findViewById(R.id.appointment_notify);
        RecyclerView treatmentList = view.findViewById(R.id.treatment_list);
        TreatmentAdapter adapter = new TreatmentAdapter(view.getContext());
        adapter.setListener(new TreatmentAdapter.TreatmentItemListener() {
            @Override
            public void onItemClick(Prescription prescription) {
                PrescriptionFragment fragment = PrescriptionFragment.newInstance(prescription);
                assert getFragmentManager() != null;
                fragment.show(getFragmentManager(),fragment.getTag());
            }

            @Override
            public void onHasAppointment() {
                view.findViewById(R.id.add_appointment_button).setVisibility(View.INVISIBLE);
                notify.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAppointmentRemove(int position, String appointmentId) {
                notify.setVisibility(View.GONE); 
                view.findViewById(R.id.add_appointment_button).setVisibility(View.VISIBLE); 
                PatientService service = new PatientService();
                service.cancelAppointment(Store.get_instance().getPatientId(), appointmentId, new OnSuccessResponse<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Toast.makeText(getContext(), "Appointment Canceled! #" + position, Toast.LENGTH_SHORT).show();
                        adapter.removeElement(position);
                        Store.get_instance().getUserAccount().getUserInfor().setCurrentAppointment(null);
                    }
                }); 
            }
        });
        treatmentList.setAdapter(adapter);
        treatmentList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        view.findViewById(R.id.add_appointment_button).setOnClickListener(v->{
            Intent i = new Intent(view.getContext(), ServiceActivity.class);
            startActivity(i);
        });

        if (!Store.get_instance().isHavingAnAppointment()) view.findViewById(R.id.add_appointment_button).setVisibility(View.VISIBLE);

            callApiAndRender(adapter, view);
        return view;
    }
    void callApiAndRender(TreatmentAdapter adapter, View view){
        PatientService service = new PatientService();
        AppointmentService appointmentService = new AppointmentService();

        if (Store.get_instance().isHavingAnAppointment()) appointmentService.getAppointmentById(Store.get_instance().getCurrentAppointment(), new OnSuccessResponse<Appointment>() {
            @Override
            public void onSuccess(Appointment currentAppointment) {
                adapter.insertCurrentAppointment(new Treatment(currentAppointment, null));
                treatments.add(new Treatment(currentAppointment, null));
            }
        });

        service.getMedicalHistory(Store.get_instance().getPatientId(), new OnSuccessResponse<Treatment[]>() {
            @Override
            public void onSuccess(Treatment[] treatmentList) {
                treatments.addAll(new ArrayList<>(Arrays.asList(treatmentList)));
                adapter.setData(new ArrayList<>(Arrays.asList(treatmentList)));
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        });

    }
}