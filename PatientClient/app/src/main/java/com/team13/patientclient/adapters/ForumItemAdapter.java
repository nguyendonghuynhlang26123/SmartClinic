package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team13.patientclient.Utils;
import com.team13.patientclient.activities.DoctorDetailActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.models.ForumModel;
import com.team13.patientclient.models.ForumModel.Topics;

import java.util.ArrayList;
import java.util.Locale;

public class ForumItemAdapter extends RecyclerView.Adapter<ForumItemAdapter.ViewHolder> {
    private final Context context;
    ArrayList<Topics> topics;
    BlogItemListener listener;
    public ForumItemAdapter(Context context){
        this.context = context;
        topics = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.blog_item, parent, false);
        return new ForumItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view = holder.itemView;
        Topics topic = topics.get(position);
        TextView questionView = view.findViewById(R.id.blog_question);
        questionView.setText(topic.getTopicString());
        TextView questionDate = view.findViewById(R.id.blog_time);
        questionDate.setText(topic.getTime());
        view.findViewById(R.id.blog_dr).setOnClickListener(v->{
            Intent i = new Intent(context, DoctorDetailActivity.class);
            context.startActivity(i);
        });
        TextView answerCount = view.findViewById(R.id.answer_count);
        answerCount.setText(R.string.no_answer);
        if(topic.hasAnswer()){
            ForumModel.Answers answer = topic.getFirstAnswer();
            view.findViewById(R.id.answer_shorten).setVisibility(View.VISIBLE);
            TextView topicAnswer = view.findViewById(R.id.topic_answer);
            TextView doctorName = view.findViewById(R.id.blog_dr);
            doctorName.setText(answer.authorName);
            String answerContent = answer.content;

            if(answerContent.length()>50){
                answerContent = Utils.shortenString(answerContent, 20);
                Button readMoreButton = view.findViewById(R.id.read_more_button);
                readMoreButton.setVisibility(View.VISIBLE);
                readMoreButton.setOnClickListener(v->{
                    topicAnswer.setText(answer.content);
                    readMoreButton.setVisibility(View.GONE);
                });
            }

            topicAnswer.setText(answerContent);
            int count = topic.getAnswerCount();
            answerCount.setText(String.format(Locale.US,"%d Answer", count));
            if(count>1){
                answerCount.setOnClickListener(v->listener.openDetail(topic));
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
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void addItem(Topics topic){
        topics.add(0, topic);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Topics> topicsData){
        topics = topicsData;
        notifyDataSetChanged();
    }

    public void setListener(BlogItemListener listener) {
        this.listener = listener;
    }

    public interface BlogItemListener{
        void openDetail(Topics topic);
    }
}
