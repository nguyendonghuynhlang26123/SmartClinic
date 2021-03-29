package com.team13.patientclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.BookAppointmentActivity;
import com.team13.patientclient.models.ServicePack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ServicePackAdapter extends RecyclerView.Adapter<ServicePackAdapter.ViewHolder> {
    ArrayList<ServicePack> servicePacks;
    private final Context context;

    public ServicePackAdapter(Context context, ArrayList<ServicePack> servicePacks){
        this.context = context;
        this.servicePacks = servicePacks;
    }

    public void setData(ArrayList<ServicePack> newData){
        servicePacks = newData;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.service_pack, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        ServicePack servicePack = servicePacks.get(position);

        ImageView serviceIcon = view.findViewById(R.id.service_icon);
        TextView serviceLabel = view.findViewById(R.id.service_label);
        TextView servicePrice = view.findViewById(R.id.service_price);

        if (!servicePack.isEmpty()){
            view.findViewById(R.id.lazylayout).setVisibility(View.VISIBLE);
            serviceLabel.setText(servicePack.getName());
            servicePrice.setText(Utils.formatingPrice(servicePack.getPrice()));
            if (servicePack.getServiceIcon().isEmpty()) serviceIcon.setImageResource(R.drawable.ic_doctor);
            else Picasso.get().load(servicePack.getServiceIcon()).into(serviceIcon);

            view.setOnClickListener(v->{
                Intent i = new Intent(context, BookAppointmentActivity.class);
                i.putExtra("Service",servicePack.getName());
                context.startActivity(i);
            });
        }
        else{
            view.findViewById(R.id.lazylayout).setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return servicePacks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
