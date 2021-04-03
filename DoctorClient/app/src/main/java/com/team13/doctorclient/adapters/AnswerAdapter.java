package com.team13.doctorclient.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Answer;
import com.team13.doctorclient.models.Question;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private final Context context;
    public final ArrayList<Answer> answers= new ArrayList<>(10);

    public AnswerAdapter(Context context, String mess) {
        this.context = context;
        this.answers.add(new Answer("001",mess));
    }
    public void add_answer(String mess){
        answers.add(new Answer("001",mess));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.answer_item_card,parent,false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer answer= answers.get(position);
        TextView textView= holder.itemView.findViewById(R.id.answer_answer);
        textView.setText(answer.getAnswer());

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }



}
