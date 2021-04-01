package com.team13.doctorclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Drug;

import java.util.ArrayList;

public class DurgAdapter extends RecyclerView.Adapter<DurgAdapter.ViewHolder> {
    private final Context context;
    public final ArrayList<Drug> drugs;

    public DurgAdapter(Context context, ArrayList<Drug> drugs) {
        this.context = context;
        this.drugs = drugs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.drug_prescription_item,parent,false);
        return new DurgAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    
}
