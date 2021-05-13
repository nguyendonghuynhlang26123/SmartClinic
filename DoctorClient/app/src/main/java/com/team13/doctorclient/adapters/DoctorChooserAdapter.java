package com.team13.doctorclient.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Doctor;

import java.util.ArrayList;

public class DoctorChooserAdapter extends BaseAdapter implements SpinnerAdapter {
    ArrayList<Doctor> doctors;
    final Context context;

    public DoctorChooserAdapter(Context context){
        this.context = context;
        doctors = new ArrayList<>();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        View view = LayoutInflater.from(context).inflate(R.layout.doctor_dropdown_item, parent);
        TextView text = new TextView(context);
        Doctor doctor = doctors.get(position);
//        TextView doctorName = view.findViewById(R.id.doctor_name);
        text.setText(doctor.getDoctorName());
        return text;
    }

    public void setData(ArrayList<Doctor> doctors){
        this.doctors = doctors;
    }

    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Doctor getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(doctors.get(position).getDoctorName());
        return text;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return doctors.isEmpty();
    }
}
