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
import com.team13.doctorclient.activities.PrescriptionViewActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class TreatmentTimelineAdapter extends RecyclerView.Adapter<TreatmentTimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Treatment> treatmentTimeline;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public TreatmentTimelineAdapter(Context context, ArrayList<Treatment> treatmentTimeline) {
        this.context = context;
        this.treatmentTimeline = treatmentTimeline;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.treatment_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentTimelineAdapter.ViewHolder holder, int position) {
        View view=holder.itemView;
        Treatment treatment = treatmentTimeline.get(position);
        Button prescriptionButton=view.findViewById(R.id.treatment_prescription_button);
        ((TextView)view.findViewById(R.id.treatment_date)).setText(treatment.getDate());
        ((TextView)view.findViewById(R.id.treatment_time)).setText(treatment.getTime());
        ((TextView)view.findViewById(R.id.treatment_symptom)).setText(treatment.getPrescription().getSymptom());
        ((TextView)view.findViewById(R.id.treatment_diagnostic)).setText(treatment.getPrescription().getDiagnose());
        ((Button)view.findViewById(R.id.treatment_service)).setText(treatment.getServicePack());
        prescriptionButton.setOnClickListener(v -> {
            Intent i= new Intent(context, PrescriptionViewActivity.class);
            i.putExtra("prescriptionId", treatment.getPrescription().getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return treatmentTimeline.size();
    }
}
