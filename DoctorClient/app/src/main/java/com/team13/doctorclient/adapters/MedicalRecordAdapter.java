package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Patient;

import java.util.ArrayList;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Patient> patients;

    public MedicalRecordAdapter(Context context, ArrayList<Patient> patients) {
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.patient_item,parent,false);
        return new MedicalRecordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patients.get(position);
        View view=holder.itemView;
        MaterialCardView patientCard= view.findViewById(R.id.medical_record);
        patientCard.setOnClickListener(v -> {
            Intent i= new Intent(context, PatientDetailActivity.class);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
