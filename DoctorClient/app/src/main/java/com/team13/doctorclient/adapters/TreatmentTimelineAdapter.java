package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.team13.doctorclient.activities.PrescriptionViewActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Treatment;

import java.io.Serializable;
import java.util.ArrayList;

public class TreatmentTimelineAdapter extends RecyclerView.Adapter<TreatmentTimelineAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Treatment> treatments;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public TreatmentTimelineAdapter(Context context) {
        this.context = context;
        this.treatments = new ArrayList<>();
    }

    public void setData(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
        notifyDataSetChanged();
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
        Treatment treatment = treatments.get(position);
        Button prescriptionButton=view.findViewById(R.id.treatment_prescription_button);

        Log.d("LONG", "position: " + position + ": " + new Gson().toJson(treatment));

        ((TextView)view.findViewById(R.id.treatment_date)).setText(treatment.getDate());
        ((TextView)view.findViewById(R.id.treatment_time)).setText(treatment.getTime());
        ((TextView)view.findViewById(R.id.treatment_symptom)).setText(treatment.getPrescription().getSymptom());
        ((TextView)view.findViewById(R.id.treatment_diagnostic)).setText(treatment.getPrescription().getDiagnose());
        ((Button)view.findViewById(R.id.treatment_service)).setText(treatment.getServicePack());

        prescriptionButton.setOnClickListener(v -> {
            Intent i= new Intent(context, PrescriptionViewActivity.class);
            i.putExtra("prescription", treatment.getPrescription());
            i.putExtra("patient", treatment.getPatient());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }


}
