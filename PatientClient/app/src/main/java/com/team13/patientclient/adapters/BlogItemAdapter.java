package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.DoctorDetailActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.models.AnonymousQuestion;
import java.util.ArrayList;
import java.util.Locale;

public class BlogItemAdapter extends RecyclerView.Adapter<BlogItemAdapter.ViewHolder> {
    private final Context context;
    ArrayList<AnonymousQuestion> questions;
    BlogItemListener listener;
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
        TextView answerCount = view.findViewById(R.id.answer_count);
        answerCount.setText(R.string.no_answer);
        if(question.hasAnswer()){
            view.findViewById(R.id.answer_shorten).setVisibility(View.VISIBLE);
            TextView blogAnswer = view.findViewById(R.id.blog_answer);
            String answer = question.getFirstAnswer();

            if(answer.length()>50){
                answer = Utils.shortenString(answer, 20);
                Button readMoreButton = view.findViewById(R.id.read_more_button);
                readMoreButton.setVisibility(View.VISIBLE);
                readMoreButton.setOnClickListener(v->{
                    blogAnswer.setText(question.getFirstAnswer());
                    readMoreButton.setVisibility(View.GONE);
                });
            }
            blogAnswer.setText(answer);
            int count = question.getAnswerCount();
            answerCount.setText(String.format(Locale.US,"%d Answer", count));
            if(count>1){
                answerCount.setOnClickListener(v->listener.openDetail(question));
                answerCount.setText(String.format(Locale.US,"%d Answers", count));
            }
        }

    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        View view = holder.itemView;
        view.findViewById(R.id.answer_shorten).setVisibility(View.GONE);
        view.findViewById(R.id.read_more_button).setVisibility(View.GONE);
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

    public void setListener(BlogItemListener listener) {
        this.listener = listener;
    }

    public interface BlogItemListener{
        void openDetail(AnonymousQuestion question);
    }
}
