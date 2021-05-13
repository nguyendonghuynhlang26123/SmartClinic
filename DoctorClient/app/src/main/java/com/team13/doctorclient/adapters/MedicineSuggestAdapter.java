package com.team13.doctorclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.DrugModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MedicineSuggestAdapter extends ArrayAdapter<DrugModel> {
    ArrayList<DrugModel> mlistData;

    public MedicineSuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mlistData = new ArrayList<>();
    }
    public void setData(ArrayList<DrugModel> list) {
        mlistData.clear();
        mlistData.addAll(list);
    }
    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Override
    public DrugModel getItem(int position) {
        return mlistData.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView != null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.medicine_list_item, parent, false);
        }

        DrugModel drug = mlistData.get(position);

        ((TextView) convertView.findViewById(R.id.medicine_name)).setText(drug.getName());
        ((TextView) convertView.findViewById(R.id.medicine_type)).setText( "" + drug.getPrice());
        if (!drug.getThumbnail().isEmpty())
            Picasso.get().load(drug.getThumbnail()).into((ImageView) convertView.findViewById(R.id.medicine_img));

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mlistData;
                    filterResults.count = mlistData.size();
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}
