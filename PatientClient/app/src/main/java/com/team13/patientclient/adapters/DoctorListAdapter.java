package com.team13.patientclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.models.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder>{
    Context context;
    ArrayList<Doctor> doctors;
    Listener listener;
    int choosingPosition;

    public DoctorListAdapter(Context context) {
        this.context = context;
        doctors = new ArrayList<>();
        listener = null;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
        Log.d("LONG", "notify!");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.doctor_choosing_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);

        Log.d("LONG", "position: " + position);
        View view = holder.itemView;
        View card = view.findViewById(R.id.doctor_card);

        card.setOnClickListener(v -> listener.onDoctorSelect(doctor.getId()));

        ((TextView)view.findViewById(R.id.doctor_name)).setText(doctor.getDoctorName());
        Picasso.get().load(doctor.getAvatarUrl()).into((ImageView) view.findViewById(R.id.doctor_img));
    }


    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public interface Listener{
        void onDoctorSelect(String doctorId);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
