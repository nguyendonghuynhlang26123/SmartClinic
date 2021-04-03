package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.TreatmentActivity;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    ArrayList<Treatment> treatments;
    final Context context;

    public TreatmentAdapter(Context context, ArrayList<Treatment> treatments) {
        this.context = context;
        this.treatments = treatments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.treatment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        Treatment treatment = treatments.get(position);
        if(position == 0){
            view.findViewById(R.id.upper_line).setVisibility(View.INVISIBLE);
        } else if (position == treatments.size() - 1){
            view.findViewById(R.id.lower_line).setVisibility(View.INVISIBLE);
        }
        TextView time = view.findViewById(R.id.treatment_date);
        time.setText(treatment.getDate());
        TextView service = view.findViewById(R.id.treatment_service);
        service.setText(treatment.getServicePack());
        TextView symptom = view.findViewById(R.id.treatment_time);
        symptom.setText("Time: " + treatment.getTime());
        TextView diagnose = view.findViewById(R.id.treatment_id);
        diagnose.setText("ID: " + treatment.getTreatmentId());
        view.findViewById(R.id.treatment_card).setOnClickListener(v->{
            Intent i = new Intent(context, TreatmentActivity.class);
            i.putExtra("prescription", treatment);
            context.startActivity(i);
        });
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
