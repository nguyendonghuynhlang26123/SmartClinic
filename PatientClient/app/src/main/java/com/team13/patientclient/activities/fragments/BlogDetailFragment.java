package com.team13.patientclient.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.patientclient.R;
import com.team13.patientclient.models.AnonymousQuestion;

public class BlogDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private AnonymousQuestion question;

    public BlogDetailFragment() {
        // Required empty public constructor
    }

    public static BlogDetailFragment newInstance(AnonymousQuestion question) {
        BlogDetailFragment fragment = new BlogDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.question = (AnonymousQuestion)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_detail, container, false);
        TextView questionContent = view.findViewById(R.id.question_content);
        questionContent.setText(question.getContent());
        LinearLayout layout = view.findViewById(R.id.question_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(16,16,16,16);
        for(String answer: question.getAnswers()){
            View answerView = LayoutInflater.from(view.getContext()).inflate(R.layout.blog_answer, container);
            answerView.setLayoutParams(params);
            TextView answerContent = answerView.findViewById(R.id.blog_answer);
            answerContent.setText(answer);
            layout.addView(answerView);
        }
        return view;
    }
}