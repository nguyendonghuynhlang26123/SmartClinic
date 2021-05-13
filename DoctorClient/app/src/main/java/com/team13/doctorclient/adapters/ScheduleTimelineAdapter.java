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

import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ScheduleItem;

import java.util.ArrayList;

public class ScheduleTimelineAdapter extends RecyclerView.Adapter<ScheduleTimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ScheduleItem> timeline;
    public ScheduleTimelineAdapter(Context context, ArrayList<ScheduleItem> timeline) {
        this.timeline = timeline;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view;
        if(viewType==0){
            view=layoutInflater.inflate(R.layout.doctor_timeline_item, parent,false);
        } else {
            view=layoutInflater.inflate(R.layout.empty_schedule_item, parent,false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        ScheduleItem scheduleItem = timeline.get(position);
//        LinearLayout linearLayout = view.findViewById(R.id.timeline_container);
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
            view.findViewById(R.id.appointment_card).setOnClickListener(v->{
                Intent i= new Intent(context, PatientDetailActivity.class);
                i.putExtra("appointment", appointment);
                i.putExtra("mode", Utils.PATIENTDETAIL_CREATE_MODE);
                context.startActivity(i);
            });

        }

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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
        }
    }

}
