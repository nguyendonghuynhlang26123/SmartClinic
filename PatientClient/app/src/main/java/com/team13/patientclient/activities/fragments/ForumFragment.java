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
import android.widget.Toast;

import com.team13.patientclient.R;
import com.team13.patientclient.adapters.ForumItemAdapter;
import com.team13.patientclient.models.ErrorResponse;
import com.team13.patientclient.models.ForumModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.ForumService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ForumFragment extends Fragment {

    View view;
    int page = 1;
    int totalPage = 1;
    int results = 1;
    ForumItemAdapter forumItemAdapter;

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
        forumItemAdapter = new ForumItemAdapter(view.getContext());
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

        view.findViewById(R.id.more_btn).setOnClickListener(this::handleMoreBtnPressed);

        callGetApi(1);
        return view;
    }

    private void handleMoreBtnPressed(View v) {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        callGetApi(page+1);
    }

    private void handlePages(){
        if (page >= totalPage)  view.findViewById(R.id.more_btn).setEnabled(false);
        else view.findViewById(R.id.more_btn).setEnabled(true);
    }

    private void callGetApi(int nextPage) {
        ForumService service = new ForumService();
        service.get(nextPage, new OnSuccessResponse<ForumModel>() {
            @Override
            public void onSuccess(ForumModel models) {
                forumItemAdapter.appendData(new ArrayList<>(Arrays.asList(models.getTopics())));
                page = nextPage;
                totalPage = models.getTotalPage();
                results = models.getNumOfTopics();
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                handlePages();
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
        view.findViewById(R.id.asking_progressbar).setVisibility(View.VISIBLE);
        v.setEnabled(false);
        if(!questionContent.isEmpty()){
            ForumModel.Topic topic = new ForumModel.Topic(questionContent);
            ForumService service = new ForumService();
            service.createTopic(topic, new OnResponse<ForumModel.Topic>() {
                @Override
                public void onRequestSuccess(ForumModel.Topic response) {
                    v.setEnabled(true);
                    view.findViewById(R.id.asking_progressbar).setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Create Topic Success!", Toast.LENGTH_SHORT).show();
                    forumItemAdapter.insertToHead(topic);
                }

                @Override
                public void onRequestFailed(ErrorResponse response) {
                    v.setEnabled(true);
                    view.findViewById(R.id.asking_progressbar).setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Create Topic Failed!", Toast.LENGTH_SHORT).show();
                }
            });
            editText.setText("");
        }
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

}