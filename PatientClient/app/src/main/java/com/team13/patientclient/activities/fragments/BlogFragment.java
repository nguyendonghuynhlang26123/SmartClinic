package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.adapters.BlogItemAdapter;
import com.team13.patientclient.models.AnonymousQuestion;

import java.util.ArrayList;
import java.util.Objects;

public class BlogFragment extends Fragment {

    ArrayList<AnonymousQuestion> questions= getEmptyQuestion();
    public BlogFragment() {
        // Required empty public constructor
    }

    public static BlogFragment newInstance() {
        BlogFragment fragment = new BlogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        BlogItemAdapter blogItemAdapter = new BlogItemAdapter(view.getContext(),questions);
        blogItemAdapter.setListener(question -> {
            BlogDetailFragment fragment = BlogDetailFragment.newInstance(question);
            assert getFragmentManager() != null;
            fragment.show(getFragmentManager(),fragment.getTag());
        });
        RecyclerView recyclerView = view.findViewById(R.id.blog_list);
        recyclerView.setAdapter(blogItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        EditText question = view.findViewById(R.id.input_question);
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        view.findViewById(R.id.submit_button).setOnClickListener(v->{
            String questionContent = question.getText().toString();
            if(!questionContent.isEmpty()){
                blogItemAdapter.addItem(new AnonymousQuestion(questionContent, "1", Utils.getCurrentDateString()));
                question.setText("");
            }
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        });
        view.findViewById(R.id.fab_ask).setOnClickListener(v->{
            NestedScrollView scrollView = view.findViewById(R.id.scroll_container);
            scrollView.smoothScrollTo(0,0);
            question.requestFocus();
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        });
        return view;
    }
    private ArrayList<AnonymousQuestion> getEmptyQuestion(){
        ArrayList<AnonymousQuestion> questions = new ArrayList<>(10);
        questions.add(new AnonymousQuestion("Why i am so beautiful","1","2/4/2021"));
        questions.add(new AnonymousQuestion("Why i am so beautiful","2","2/4/2021"));
        questions.add(new AnonymousQuestion("Why i am so beautiful","3","2/4/2021"));
        questions.add(new AnonymousQuestion("Why i am so beautiful","4","2/4/2021"));
        questions.add(new AnonymousQuestion("Why i am so beautiful","5","2/4/2021"));
        questions.add(new AnonymousQuestion("Why i am so beautiful","6","2/4/2021"));
        questions.get(1).setAnswers(new String[]{"oke","yeah","oleya"});
        questions.get(3).setAnswers(new String[]{"okelaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"});
        return questions;
    }
}