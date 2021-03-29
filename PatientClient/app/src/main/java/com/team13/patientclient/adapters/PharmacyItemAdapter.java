package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.DrugActivity;
import com.team13.patientclient.models.MedicineModel;

import java.util.ArrayList;

public class PharmacyItemAdapter extends RecyclerView.Adapter<PharmacyItemAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<MedicineModel> drugs;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public PharmacyItemAdapter(Context context, ArrayList<MedicineModel> drugs){
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

    public void setData(ArrayList<MedicineModel> drugs){
        this.drugs = drugs;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        String url = drugs.get(position).getThumbnail();
        if (!url.isEmpty()) Picasso.get().load(url).into((ImageView)view.findViewById(R.id.pharmacy_item_img));
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
