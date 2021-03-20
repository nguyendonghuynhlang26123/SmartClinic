package com.team13.patientclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
import com.team13.patientclient.models.Department;

import java.util.ArrayList;

public class DepartmentItemAdapter extends RecyclerView.Adapter<DepartmentItemAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Department> departments;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public DepartmentItemAdapter(Context context, ArrayList<Department> departments){
        this.context = context;
        this.departments = departments;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.department_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return departments.size();
    }
}
