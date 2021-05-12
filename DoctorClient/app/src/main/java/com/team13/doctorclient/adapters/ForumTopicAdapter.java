package com.team13.doctorclient.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team13.doctorclient.R;
import com.team13.doctorclient.models.ForumModel;

import java.util.ArrayList;
import java.util.Locale;

public class ForumTopicAdapter extends RecyclerView.Adapter<ForumTopicAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ForumModel.Topic> topics;
    ForumItemListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public ForumTopicAdapter(Context context) {
        this.topics = new ArrayList<>(5);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.forum_topic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View view=holder.itemView;
        ForumModel.Topic topic = topics.get(position);
        TextView lineColor= view.findViewById(R.id.lineColor);
        TextView topicContent = view.findViewById(R.id.patient_question);
        topicContent.setText(topic.getTopicString());
        TextView answerCount = view.findViewById(R.id.answer_count);
        answerCount.setText(R.string.no_answer);
        if(topic.hasAnswer()){
            int count = topic.getAnswerCount();
            if(count > 1) answerCount.setText(String.format(Locale.US,"%d Answers", count));
            else answerCount.setText(String.format(Locale.US,"%d Answer", count));
        }
//        switch (question.getState()){
//            case 0:
//                lineColor.setBackgroundColor(view.getResources().getColor(R.color.dark_pink));
//                break;
//            case 1:
//                lineColor.setBackgroundColor(view.getResources().getColor(R.color.indigo));
//                break;
//            default:
//                break;
//        }
        ImageButton detailQuestion= view.findViewById(R.id.forum_detail_button);
        detailQuestion.setOnClickListener(v -> listener.openDetail(topic));

        if(position==getItemCount()-1 && getItemCount()>=5){
            listener.loadNextPage();
        }
    }

    public void reset(){
        topics.clear();
        notifyDataSetChanged();
    }

    public void appendData(ArrayList<ForumModel.Topic> topicData){
        topics.addAll(topicData);
        notifyDataSetChanged();
    }

    public void setListener(ForumItemListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public interface ForumItemListener{
        void openDetail(ForumModel.Topic topic);
        void loadNextPage();
    }
}
