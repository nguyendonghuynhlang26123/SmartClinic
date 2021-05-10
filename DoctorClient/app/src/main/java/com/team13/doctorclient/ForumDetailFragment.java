package com.team13.doctorclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team13.doctorclient.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private Question question;

    public ForumDetailFragment() {
        // Required empty public constructor
    }

    public static ForumDetailFragment newInstance(Question question) {
        ForumDetailFragment fragment = new ForumDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.question = (Question)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum_detail, container, false);

        return view;
    }
}