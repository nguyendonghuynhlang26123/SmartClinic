package com.team13.patientclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlogItemAdapter extends RecyclerView.Adapter<BlogItemAdapter.ViewHolder> {
    private final Context context;
    public BlogItemAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.blog_item, parent, false);
        return new BlogItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
//        view.findViewById(R.id.blog_dr).setOnClickListener(v->{
//            Intent i = new Intent(context, DoctorDetailActivity.class);
//            context.startActivity(i);
//        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
