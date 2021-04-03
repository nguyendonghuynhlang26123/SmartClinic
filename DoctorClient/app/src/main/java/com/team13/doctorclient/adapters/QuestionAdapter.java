package com.team13.doctorclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.team13.doctorclient.AnswerActivity;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Question> questions;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.questions = questions;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.question_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        Question question = questions.get(position);
        TextView lineColor= view.findViewById(R.id.lineColor);

        switch (question.getState()){
            case 0:
                lineColor.setBackgroundColor(view.getResources().getColor(R.color.dark_pink));
                break;
            case 1:
                lineColor.setBackgroundColor(view.getResources().getColor(R.color.indigo));
                break;
            default:
                break;
        }
        ImageButton detailQuestion= view.findViewById(R.id.showDetail_button);
        detailQuestion.setOnClickListener(v -> {
            Intent i= new Intent(context, AnswerActivity.class);
            i.putExtra("question",question);
            context.startActivity(i);
            question.setState(1);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


}
