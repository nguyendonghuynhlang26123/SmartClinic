package com.team13.doctorclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;

import java.util.ArrayList;

public class AppointmentScheduleAdapter extends RecyclerView.Adapter<AppointmentScheduleAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Appointment> appointments;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public AppointmentScheduleAdapter(Context context, ArrayList<Appointment> appointments) {
        this.appointments = appointments;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.appointment_schedule_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }


}
