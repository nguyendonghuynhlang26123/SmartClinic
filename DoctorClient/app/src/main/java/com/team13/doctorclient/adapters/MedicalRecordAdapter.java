package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.team13.doctorclient.activities.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.PatientModel;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<PatientModel> patients;

    public MedicalRecordAdapter(Context context) {
        this.context = context;
        this.patients = new ArrayList<>();
    }

    public void setData(ArrayList<PatientModel> data){
        this.patients = data;
        notifyDataSetChanged();
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
        PatientModel patient = patients.get(position);
        View view=holder.itemView;
        MaterialCardView patientCard= view.findViewById(R.id.medical_record);
        patientCard.setOnClickListener(v -> {
            Intent i= new Intent(context, PatientDetailActivity.class);
            i.putExtra("status","REVIEW");
            i.putExtra("patient", patient);
            context.startActivity(i);
        });
        ((TextView)view.findViewById(R.id.treatment_patient_name)).setText(patient.getName());
        Picasso.get().load(patient.getAvatarUrl()).into((ImageView) view.findViewById(R.id.patient_img));
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
