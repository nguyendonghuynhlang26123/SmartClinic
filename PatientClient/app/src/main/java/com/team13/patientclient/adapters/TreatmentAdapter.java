package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.TreatmentActivity;
import com.team13.patientclient.activities.fragments.PrescriptionFragment;
import com.team13.patientclient.models.Prescription;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    ArrayList<Treatment> treatments;
    final Context context;
    TreatmentItemListener listener;
    public TreatmentAdapter(Context context, TreatmentItemListener listener) {
        this.context = context;
        this.treatments = new ArrayList<>();
        this.listener = listener;
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
        TextView time = view.findViewById(R.id.treatment_date);
        time.setText(treatment.getDate());
        TextView service = view.findViewById(R.id.treatment_service);
        service.setText(treatment.getServicePack());
        TextView symptom = view.findViewById(R.id.treatment_time);
        symptom.setText("Time: " + treatment.getTime());
        MaterialCardView treatmentCard = view.findViewById(R.id.treatment_card);
        treatmentCard.setOnClickListener(v->{
            Intent i = new Intent(context, TreatmentActivity.class);
            i.putExtra("prescription", treatment);
            context.startActivity(i);
        });
        if(treatment.getPrescription()!=null){
            ImageButton prescription = view.findViewById(R.id.treatment_prescription);
            prescription.setVisibility(View.VISIBLE);
            prescription.setOnClickListener(v-> listener.onItemClick(treatment.getPrescription()));
        }
        if(position == 0){
            view.findViewById(R.id.upper_line).setVisibility(View.INVISIBLE);
            if(treatment.getStatus().equals("PENDING")){
                treatmentCard.setStrokeColor(view.getResources().getColor(R.color.purple_dark));
                treatmentCard.setStrokeWidth(8);
                time.setTextColor(view.getResources().getColor(R.color.red));
                listener.onHasAppointment();
                ImageButton removeButton = view.findViewById(R.id.treatment_remove_button);
                removeButton.setVisibility(View.VISIBLE);
                removeButton.setOnClickListener(v->{
                    listener.onAppointmentRemove(treatments.get(position).getAppointment().getId());

                    treatments.remove(position);
                    notifyDataSetChanged();
                });
            }
        } else if (position == treatments.size() - 1){
            view.findViewById(R.id.lower_line).setVisibility(View.INVISIBLE);
        }
        if(treatment.getStatus().equals("CANCEL")){
            treatmentCard.setStrokeColor(view.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }

    public void setData(ArrayList<Treatment> newData){
        this.treatments = newData;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface TreatmentItemListener{
        void onItemClick(Prescription prescription);
        void onHasAppointment();
        void onAppointmentRemove(String appointmentId);
    }
}
