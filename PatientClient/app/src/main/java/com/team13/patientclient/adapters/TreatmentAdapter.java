package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.TreatmentActivity;
import com.team13.patientclient.models.Prescription;
import com.team13.patientclient.models.Treatment;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    ArrayList<Treatment> treatments;
    final Context context;
    TreatmentItemListener listener;
    public TreatmentAdapter(Context context) {
        this.context = context;
        this.treatments = new ArrayList<>();
    }

    public void setListener( TreatmentItemListener listener){
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
        ImageButton stateIcon = view.findViewById(R.id.timeline_marker);

        //Set icon
        if (treatment.getPrescription() == null) stateIcon.setImageResource(R.drawable.ic_pending);
        else stateIcon.setImageResource(R.drawable.ic_check);

        //Set date view
        TextView time = view.findViewById(R.id.treatment_date);
        String[] temp = treatment.getDate().split("/");
        time.setText(String.format("%s/%s\n%s", temp[0], temp[1], temp[2]));
        //Set service view
        TextView service = view.findViewById(R.id.treatment_service);
        service.setText(Utils.shortenString(treatment.getServicePack(),20));
        //Set TreatmentView
        TextView treatmentTime = view.findViewById(R.id.treatment_time);
        treatmentTime.setText(context.getString(R.string.treatment_time, treatment.getTime()));


        MaterialCardView treatmentCard = view.findViewById(R.id.treatment_card);
        treatmentCard.setStrokeWidth(0);
        treatmentCard.setOnClickListener(v->{
            Intent i = new Intent(context, TreatmentActivity.class);
            i.putExtra("prescription", treatment);
            context.startActivity(i);
        });
        //Setup icon at right corner
        if(treatment.getPrescription()!=null){
            ImageButton prescription = view.findViewById(R.id.treatment_prescription);
            prescription.setVisibility(View.VISIBLE);
            prescription.setOnClickListener(v-> listener.onItemClick(treatment.getPrescription()));
        }
        //Setup remove button
        ImageButton removeButton = view.findViewById(R.id.treatment_remove_button);
        removeButton.setVisibility(View.INVISIBLE);
        if(position == 0 && Store.get_instance().isHavingAnAppointment()){
            view.findViewById(R.id.upper_line).setVisibility(View.INVISIBLE);
            treatmentCard.setStrokeColor(view.getResources().getColor(R.color.purple_dark));
            treatmentCard.setStrokeWidth(8);
            time.setTextColor(view.getResources().getColor(R.color.red));
            listener.onHasAppointment();
            removeButton.setVisibility(View.VISIBLE);
            removeButton.setOnClickListener(v-> listener.onAppointmentRemove(position, Store.get_instance().getCurrentAppointmentId()));
        }
        else if (position == 0){
            time.setTextColor(view.getResources().getColor(R.color.black));
        }
        else if (position == treatments.size() - 1){
            view.findViewById(R.id.lower_line).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }

    public void addData(ArrayList<Treatment> newData){
        this.treatments.addAll(newData);
        this.notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        View view = holder.itemView;
        view.findViewById(R.id.treatment_prescription).setVisibility(View.INVISIBLE);
        TextView time = view.findViewById(R.id.treatment_date);
        time.setTextColor(view.getResources().getColor(R.color.black));
    }

    public void insertCurrentAppointment(Treatment treatment){
        this.treatments.add(0, treatment);
        this.notifyDataSetChanged();
    }

    public void removeElement(int position){
        treatments.remove(position);
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
        void onAppointmentRemove(int position, String appointmentId);
    }
}
