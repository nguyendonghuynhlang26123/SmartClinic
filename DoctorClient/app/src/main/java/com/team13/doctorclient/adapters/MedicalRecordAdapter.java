package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Patient;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Treatment> treatments;

    public MedicalRecordAdapter(Context context, ArrayList<Treatment> treatments) {
        this.context = context;
        this.treatments = treatments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.medical_record_item,parent,false);
        return new MedicalRecordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Treatment treatment = treatments.get(position);
        View view=holder.itemView;
        MaterialCardView patientCard= view.findViewById(R.id.medical_record);
        patientCard.setOnClickListener(v -> {
            Intent i= new Intent(context, PatientDetailActivity.class);
            i.putExtra("status","REVIEW");
            context.startActivity(i);
        });
        ((TextView)view.findViewById(R.id.treatment_patient_name)).setText(treatment.getAppointment().getPatientId());
        ((TextView)view.findViewById(R.id.treatment_diagnostic)).setText(treatment.getPrescription().getDiagnose());
        ((Button)view.findViewById(R.id.treatment_service)).setText(treatment.getServicePack());
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
