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

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ScheduleItem;

import java.util.ArrayList;

public class PendingAppointmentAdapter extends RecyclerView.Adapter<PendingAppointmentAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ScheduleItem> timeline;
    private Listener listener;
    public PendingAppointmentAdapter(Context context) {
        this.timeline = new ArrayList<>();
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view;
        if(viewType == 0) view=layoutInflater.inflate(R.layout.nurse_timeline_item, parent,false);
        else view = layoutInflater.inflate(R.layout.empty_schedule_item, parent,false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        ScheduleItem scheduleItem = timeline.get(position);
        TextView timeView = view.findViewById(R.id.timeline_date);
        String time = scheduleItem.getTime();
        timeView.setText(time);
        if(getItemViewType(position)==0){
            Appointment appointment = scheduleItem.getAppointment();
            TextView note = view.findViewById(R.id.appointment_note);
            note.setText(appointment.getNote());
            TextView patientName = view.findViewById(R.id.appointment_patient_name);
            patientName.setText(appointment.getPatientId());
            Button treatment = view.findViewById(R.id.appointment_service);
            treatment.setText(appointment.getService().getName());
            TextView status=view.findViewById(R.id.status);
            status.setText(appointment.getStatus());
            Button accept= view.findViewById(R.id.accept_patient);
            if(!appointment.getStatus().equals("CHECKED_IN")){
                accept.setVisibility(View.VISIBLE);
            }
            accept.setOnClickListener(v -> {
//                status.setText("CHECKED_IN");
                //appointment.setStatus("CHECKED_IN");
                if (listener != null) listener.onAccept(appointment.getId(), appointment.getPatientId());
                notifyItemChanged(position);
            });
//            Button update= view.findViewById(R.id.update_status);
//            update.setOnClickListener(v -> {
//                setStatus("Update");//?????
//                appointment.setStatus("Update");
//
//            });
//            appointmentCard.setOnClickListener(v -> {
//                Intent i= new Intent(context, PatientDetailActivity.class);
//                i.putExtra("appointment", appointment);
//                context.startActivity(i);
//            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        View view = holder.itemView;
        Button accept_button =  view.findViewById(R.id.accept_patient);
        if(accept_button!=null) accept_button.setVisibility(View.GONE);

    }

    @Override
    public int getItemViewType(int position) {
        if(timeline.get(position).isSeized) return 0;
        return -1;
    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }

    public void setData(ArrayList<ScheduleItem> newData) {
        this.timeline = newData;
        notifyDataSetChanged();;
    }

    public void updateStatus(String appointmentId, String status) {
        for (int i = 0; i < timeline.size(); i++) {
            ScheduleItem item = timeline.get(i);
            if (item.isSeized && item.getAppointment().getId().equals(appointmentId)){
                item.getAppointment().setStatus(status);
                notifyItemChanged(i);
                return;
            }
        }
    }

    public ArrayList<ScheduleItem> getData(){
        return timeline;
    }

    public void setListener(Listener l) { this.listener = l;}

    public interface Listener {
        void onAccept(String appointmentId, String patientId);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if(viewType == 0){

            }
        }
    }
}
