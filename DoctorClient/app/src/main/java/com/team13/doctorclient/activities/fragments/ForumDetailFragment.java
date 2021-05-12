package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.ForumModel;
import com.team13.doctorclient.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private ForumModel.Topic topic;

    public ForumDetailFragment() {
        // Required empty public constructor
    }

    public static ForumDetailFragment newInstance(ForumModel.Topic topic) {
        ForumDetailFragment fragment = new ForumDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.topic = (ForumModel.Topic) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum_detail, container, false);
        TextView questionContent = view.findViewById(R.id.question_content);
        questionContent.setText(topic.getTopicString());
        if(topic.hasAnswer()){
            LinearLayout layout = view.findViewById(R.id.question_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16,16,16,16);
            for(ForumModel.Answer answer: topic.getAnswers()){
                View answerView = LayoutInflater.from(view.getContext()).inflate(R.layout.forum_answer, container);
                answerView.setLayoutParams(params);
                TextView answerAuthor = answerView.findViewById(R.id.topic_author);
                answerAuthor.setText(answer.authorName);
                TextView answerContent = answerView.findViewById(R.id.topic_answer);
                answerContent.setText(answer.content);
                layout.addView(answerView);
            }
        }
        return view;
    }
}