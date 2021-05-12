package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Store;
import com.team13.doctorclient.models.ForumModel;
import com.team13.doctorclient.models.Question;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private ForumModel.Topic topic;
    LinearLayout.LayoutParams params;
    LinearLayout layout;
    View view;
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
        view = inflater.inflate(R.layout.fragment_forum_detail, container, false);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,16,0,16);
        layout= view.findViewById(R.id.question_container);
        TextView questionContent = view.findViewById(R.id.question_content);
        questionContent.setText(topic.getTopicString());
        if(topic.hasAnswer()){
            for(ForumModel.Answer answer: topic.getAnswers()){
               addAnswerToLayout(answer.authorName, answer.content);
            }
        }
        EditText inputAnswer = view.findViewById(R.id.input_answer);
        view.findViewById(R.id.send_button).setOnClickListener(v -> {
            String answer = inputAnswer.getText().toString();
            if(!answer.isEmpty()){
                handleSubmit(answer);
                inputAnswer.setText("");
            }
        });
        return view;
    }

    void addAnswerToLayout(String author, String answer){
        View answerView = LayoutInflater.from(getContext()).inflate(R.layout.forum_answer, null);
        answerView.setLayoutParams(params);
        TextView answerAuthor = answerView.findViewById(R.id.topic_author);
        answerAuthor.setText(author);
        TextView answerContent = answerView.findViewById(R.id.topic_answer);
        answerContent.setText(answer);
        layout.addView(answerView, layout.getChildCount()-1);
    }

    void handleSubmit(String answer){
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
        addAnswerToLayout(Store.get_instance().getName(), answer);
    }
}