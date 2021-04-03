
package com.team13.doctorclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team13.doctorclient.adapters.QuestionAdapter;
import com.team13.doctorclient.models.Question;

import java.util.ArrayList;

    public class DoctorBlogFragment extends Fragment {

        RecyclerView questionList;

        public static DoctorBlogFragment newInstance() {
            return new DoctorBlogFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_blog, container, false);
            questionList = view.findViewById(R.id.question_list);
            QuestionAdapter questionAdapter = new QuestionAdapter(view.getContext(), getQuestion());
            questionList.setAdapter(questionAdapter);
            questionList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            return view;
        }


        public ArrayList<Question> getQuestion() {
            ArrayList<Question> questions = new ArrayList<Question>(5);
            for (int i = 0; i < 7; ++i) {
                questions.add(new Question("P001", "D001", "27th Mar", "1:30pm - 2:30pm", "Why I am so beautiful"));
            }
            questions.get(2).setState(2);
            questions.get(4).setState(1);
            return questions;
        }
    }