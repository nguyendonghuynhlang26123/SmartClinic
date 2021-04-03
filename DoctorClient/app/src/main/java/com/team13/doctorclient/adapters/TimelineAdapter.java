package com.team13.doctorclient.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Timeline;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Timeline> timelineArrayList;
    public TimelineAdapter(Context context, ArrayList<Timeline> timelineArrayList) {
        this.timelineArrayList =timelineArrayList ;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.timeline_item,parent,false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        Timeline timeline = timelineArrayList.get(position);
        LinearLayout linearLayout = view.findViewById(R.id.timeline_container);
        if(timeline.isSeized){
            View smallview = LayoutInflater.from(context).inflate(R.layout.timeline_item_card,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(smallview,params);
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
        return timelineArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView mTimelineView;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
        }
    }

}
