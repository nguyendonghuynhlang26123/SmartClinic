package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.activities.DoctorDetailActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.models.AnonymousQuestion;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BlogItemAdapter extends RecyclerView.Adapter<BlogItemAdapter.ViewHolder> {
    private final Context context;
    ArrayList<AnonymousQuestion> questions;
    public BlogItemAdapter(Context context, ArrayList<AnonymousQuestion> questions){
        this.context = context;
        this.questions = questions;
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
        AnonymousQuestion question = questions.get(position);
        TextView questionView = view.findViewById(R.id.blog_question);
        questionView.setText(question.getContent());
        TextView questionDate = view.findViewById(R.id.blog_time);
        questionDate.setText(question.getDate());
        view.findViewById(R.id.blog_dr).setOnClickListener(v->{
            Intent i = new Intent(context, DoctorDetailActivity.class);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void addItem(AnonymousQuestion question){
        questions.add(0, question);
        notifyDataSetChanged();
    }
}
