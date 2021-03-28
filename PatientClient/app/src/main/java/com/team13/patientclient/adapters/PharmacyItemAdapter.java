package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
import com.team13.patientclient.activities.DrugActivity;
import com.team13.patientclient.models.Drug;

import java.util.ArrayList;

public class PharmacyItemAdapter extends RecyclerView.Adapter<PharmacyItemAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Drug> drugs;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public PharmacyItemAdapter(Context context, ArrayList<Drug> drugs){
        this.context = context;
        this.drugs = drugs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pharmacy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        view.setOnClickListener(v->{
            Intent i = new Intent(context, DrugActivity.class);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
