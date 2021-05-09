package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ScheduleItem;

import java.util.ArrayList;

public class PendingAppointmentAdapter extends RecyclerView.Adapter<PendingAppointmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ScheduleItem> timeline;
    MaterialCardView appointmentCard;
    TextView status;
    public PendingAppointmentAdapter(Context context, ArrayList<ScheduleItem> timeline) {
        this.timeline = timeline;
        this.context=context;
    }

    public PendingAppointmentAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.schedule_timeline_item,parent,false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        ScheduleItem scheduleItem = timeline.get(position);
        LinearLayout linearLayout = view.findViewById(R.id.timeline_container);
        TextView timeView = view.findViewById(R.id.timeline_time);
        String time = scheduleItem.getTime();
        timeView.setText(time);
        if(scheduleItem.isSeized){
            Appointment appointment = scheduleItem.getAppointment();
            View smallView = LayoutInflater.from(context).inflate(R.layout.accept_patient_item,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(smallView,params);
            appointmentCard = view.findViewById(R.id.appointment_accept_card);
            TextView note = smallView.findViewById(R.id.appointment_note);
            note.setText(appointment.getNote());
            TextView patientName = smallView.findViewById(R.id.appointment_patient_name);
            patientName.setText(appointment.getPatientId());
            Button treatment = smallView.findViewById(R.id.appointment_service);
            treatment.setText(appointment.getService().getName());
            status=smallView.findViewById(R.id.status);
            setStatus(appointment.getStatus());

            Button accept= smallView.findViewById(R.id.accept_patient);
            accept.setOnClickListener(v -> {
                setStatus("Check in");
                appointment.setStatus("Check in");
            });
            Button update= smallView.findViewById(R.id.update_status);
            update.setOnClickListener(v -> {
                setStatus("Update");//?????
                appointment.setStatus("Update");

            });
//            appointmentCard.setOnClickListener(v -> {
//                Intent i= new Intent(context, PatientDetailActivity.class);
//                i.putExtra("appointment", appointment);
//                context.startActivity(i);
//            });
        } else {
            TextView line = new TextView(context);
            line.setBackgroundColor(view.getResources().getColor(R.color.gray));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Math.round(convertDpToPixel(2,context)));
            params.setMargins(Math.round(convertDpToPixel(4,context)),Math.round(convertDpToPixel(16,context)),0,Math.round(convertDpToPixel(16,context)));
            linearLayout.addView(line, params);
        }
    }

    private float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView timelineMarker;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            timelineMarker = itemView.findViewById(R.id.timeline_marker);
            timelineMarker.initLine(viewType);
            timelineMarker.setMarkerColor(itemView.getResources().getColor(R.color.dark_pink));
        }
    }

    private void setStatus(String mess){
        status.setText(mess);
    }

}
