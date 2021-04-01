package com.team13.doctorclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.PatientTimeline;

import java.util.ArrayList;

public class PatientTimelineAdapter  extends RecyclerView.Adapter<PatientTimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<PatientTimeline> patientTimelineArrayList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView mTimelineView;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.patient_timeline);
            mTimelineView.initLine(viewType);
        }
    }
    public PatientTimelineAdapter(Context context, ArrayList<PatientTimeline> patientTimelineArrayList) {
        this.context = context;
        this.patientTimelineArrayList = patientTimelineArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.patient_timeline_item,parent,false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientTimelineAdapter.ViewHolder holder, int position) {
    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return patientTimelineArrayList.size();
    }
}
