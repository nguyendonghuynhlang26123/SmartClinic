package com.team13.patientclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.team13.patientclient.R;
import com.team13.patientclient.Utils;
import com.team13.patientclient.adapters.ForumItemAdapter;
import com.team13.patientclient.models.ForumModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.ForumService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ForumFragment extends Fragment {

    ForumModel forumModel;
    View view;
    int page = 1;
    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance() {
        ForumFragment fragment = new ForumFragment();
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
        view = inflater.inflate(R.layout.fragment_blog, container, false);
        forumModel = new ForumModel();
        ForumItemAdapter forumItemAdapter = new ForumItemAdapter(view.getContext());
        forumItemAdapter.setListener(topic -> {
//            AnswerDetailFragment fragment = AnswerDetailFragment.newInstance(question);
//            assert getFragmentManager() != null;
//            fragment.show(getFragmentManager(),fragment.getTag());
        });
        RecyclerView recyclerView = view.findViewById(R.id.blog_list);
        recyclerView.setAdapter(forumItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));

        view.findViewById(R.id.submit_button).setOnClickListener(this::handleSubmit);

        view.findViewById(R.id.fab_ask).setOnClickListener(v->{
            handleAskBtnPressed();
        });

        callGetApi(forumItemAdapter, 1);
        return view;
    }

    private void callGetApi(ForumItemAdapter forumItemAdapter, int nextPage) {
        ForumService service = new ForumService();
        service.get(nextPage, new OnSuccessResponse<ForumModel>() {
            @Override
            public void onSuccess(ForumModel models) {
                forumModel = models;
                Log.d("LONG", new Gson().toJson(models));
                forumItemAdapter.setData(new ArrayList<>(Arrays.asList(models.getTopics())));
                page = nextPage;
            }
        });
    }

    private void handleAskBtnPressed() {
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editText = view.findViewById(R.id.input_question);
        NestedScrollView scrollView = view.findViewById(R.id.scroll_container);
        scrollView.smoothScrollTo(0,0);
        editText.requestFocus();
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void handleSubmit(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editText = view.findViewById(R.id.input_question);
        String questionContent = editText.getText().toString();
        if(!questionContent.isEmpty()){
//            forumItemAdapter.addItem(new AnonymousQuestion(questionContent, "1", Utils.getCurrentDateString()));
            editText.setText("");
        }
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

}