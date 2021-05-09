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



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<AnonymousQuestion> questions= getEmptyQuestion();
    public BlogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        BlogItemAdapter blogItemAdapter = new BlogItemAdapter(view.getContext(),questions);
        blogItemAdapter.setListener(question -> {
            BlogDetailFragment fragment = BlogDetailFragment.newInstance(question);
            fragment.show(getFragmentManager(),fragment.getTag());
        });
        RecyclerView recyclerView = view.findViewById(R.id.blog_list);
        recyclerView.setAdapter(blogItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        EditText question = view.findViewById(R.id.input_question);
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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