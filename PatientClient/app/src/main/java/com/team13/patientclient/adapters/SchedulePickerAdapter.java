package com.team13.patientclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.R;
import com.team13.patientclient.Store;
import com.team13.patientclient.Utils;
import com.team13.patientclient.models.HospitalModel;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SchedulePickerAdapter  extends RecyclerView.Adapter<SchedulePickerAdapter.ViewHolder> {
    Context context;
    ArrayList<String> days;
    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

    public SchedulePickerAdapter(Context context, String[] days){
        this.context = context;
        this.days = new ArrayList<String>(Arrays.asList(days));
    }

    @NonNull
    @Override
    public SchedulePickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.time_picking_item, parent, false);
        return new SchedulePickerAdapter.ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull SchedulePickerAdapter.ViewHolder holder, int position) {
        View view = holder.itemView;
        renderTimeChoice(view, days.get(position));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void renderTimeChoice(View view, String day){
        LinearLayout layout = view.findViewById(R.id.time_pick_layout);
        HospitalModel hospital = Store.get_instance().getHospital();
        ArrayList<String> shifts = Utils.generateTimes(hospital.getOpenTime(), hospital.getCloseTime(), 30 );

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Math.round(convertDpToPixel(4,view.getContext()));
        params.bottomMargin = Math.round(convertDpToPixel(4,view.getContext()));

        for (String s : shifts) {
            RadioButton timeButton = new RadioButton(view.getContext());
            timeButton.setText(s);
            timeButton.setLayoutParams(params);

            layout.addView(timeButton);
        }
    }
}
