package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.PatientDetailActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.DoctorTimeline;

import java.util.ArrayList;

public class DoctorTimelineAdapter extends RecyclerView.Adapter<DoctorTimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<DoctorTimeline> doctorTimelineArrayList;
    MaterialCardView patientCardview;
    public DoctorTimelineAdapter(Context context, ArrayList<DoctorTimeline> doctorTimelineArrayList) {
        this.doctorTimelineArrayList = doctorTimelineArrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.doctor_timeline_item,parent,false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        DoctorTimeline doctorTimeline = doctorTimelineArrayList.get(position);
        LinearLayout linearLayout = view.findViewById(R.id.timeline_container);
        if(doctorTimeline.isSeized){
            View smallview = LayoutInflater.from(context).inflate(R.layout.timeline_item_card,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(smallview,params);
            patientCardview= view.findViewById(R.id.patient_line);
            patientCardview.setOnClickListener(v -> {
                Intent i= new Intent(context, PatientDetailActivity.class);
                context.startActivity(i);
            });
        } else {
            TextView line = new TextView(context);
            line.setBackgroundColor(view.getResources().getColor(R.color.gray));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Math.round(convertDpToPixel(2,context)));
            params.setMargins(Math.round(convertDpToPixel(4,context)),Math.round(convertDpToPixel(16,context)),0,Math.round(convertDpToPixel(16,context)));
            linearLayout.addView(line, params);
        }
    }

    private float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return doctorTimelineArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView mTimelineView;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.doctorTimeline);
            mTimelineView.initLine(viewType);
        }
    }

}
