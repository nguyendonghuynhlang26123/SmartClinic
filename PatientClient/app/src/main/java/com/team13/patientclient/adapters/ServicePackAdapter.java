package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.service_pack, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        ServicePack servicePack = servicePacks.get(position);
        ImageView serviceIcon = view.findViewById(R.id.service_icon);
        serviceIcon.setImageResource(servicePack.serviceIcon);
        TextView serviceLabel = view.findViewById(R.id.service_label);
        serviceLabel.setText(servicePack.name);
        TextView servicePrice = view.findViewById(R.id.service_price);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));
        servicePrice.setText(format.format(servicePack.price));
        view.setOnClickListener(v->{
            Intent i = new Intent(context, BookAppointmentActivity.class);
            i.putExtra("Service",servicePack.name);
            context.startActivity(i);
        });
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