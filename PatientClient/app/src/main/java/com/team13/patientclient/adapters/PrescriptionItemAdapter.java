package com.team13.patientclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
import com.team13.patientclient.models.DrugDetail;

import java.util.ArrayList;

public class PrescriptionItemAdapter extends RecyclerView.Adapter<PrescriptionItemAdapter.ViewHolder> {
    final Context context;
    ArrayList<DrugDetail> drugs;

    public PrescriptionItemAdapter(Context context, ArrayList<DrugDetail> drugs) {
        this.context = context;
        this.drugs = drugs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prescription_drug_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        DrugDetail drug = drugs.get(position);
        TextView number = view.findViewById(R.id.prescription_item_number);
        number.setText(String.valueOf(position+1));
        TextView name = view.findViewById(R.id.prescription_item_name);
        name.setText(drug.getDrugModel().getName());
        TextView quantity = view.findViewById(R.id.prescription_item_quantity);
        quantity.setText(String.valueOf(drug.getQuantity()));
        TextView note = view.findViewById(R.id.prescription_item_note);
        note.setText(drug.getNote());
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
