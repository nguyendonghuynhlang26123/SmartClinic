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

import com.github.vipulasri.timelineview.TimelineView;
import com.team13.doctorclient.PrescriptionViewActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Treatment;

import java.util.ArrayList;

public class TreatmentTimelineAdapter extends RecyclerView.Adapter<TreatmentTimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Treatment> treatmentTimeline;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView timelineViewMarker;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            timelineViewMarker = (TimelineView) itemView.findViewById(R.id.treatment_timeline_marker);
            timelineViewMarker.initLine(viewType);
            timelineViewMarker.setMarkerColor(itemView.getResources().getColor(R.color.dark_pink));
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
        return new ViewHolder(view, viewType);
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
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return treatmentTimeline.size();
    }
}
