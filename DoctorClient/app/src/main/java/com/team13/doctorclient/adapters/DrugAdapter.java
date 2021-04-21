package com.team13.doctorclient.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Drug;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {
    private final Context context;
    public ArrayList<Drug> drugs;
    boolean editable = false;

    public DrugAdapter(Context context, ArrayList<Drug> drugs, boolean editable) {
        this.context = context;
        this.drugs = drugs;
        this.editable = editable;
    }
    public DrugAdapter(Context context, boolean editable){
        this.context=context;
        drugs= new ArrayList<>();
        this.editable = editable;
    }
    public void setData(ArrayList<Drug> addDrugArrayList){
        drugs= addDrugArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view;
        if(!editable){
            view=layoutInflater.inflate(R.layout.drug_prescription_item,parent,false);
        } else {
            view=layoutInflater.inflate(R.layout.draft_prescription_item,parent,false);
        }
        return new DrugAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view= holder.itemView;
        switch (view.getSourceLayoutResId()){
            case R.layout.drug_prescription_item:
                ((TextView)view.findViewById(R.id.drug_stt)).setText(String.valueOf(position+1));
                break;
            case R.layout.draft_prescription_item:
                view.findViewById(R.id.drug_remove).setOnClickListener(v->{
                    drugs.remove(position);
                    notifyDataSetChanged();
                });
        }
        Drug drug= drugs.get(position);
        ((TextView)view.findViewById(R.id.drug_name)).setText(drug.getDrugName());
        ((TextView)view.findViewById(R.id.drug_quality)).setText(drug.getDrugQuality());
        ((TextView)view.findViewById(R.id.drug_note)).setText(drug.getDrugNote());

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
