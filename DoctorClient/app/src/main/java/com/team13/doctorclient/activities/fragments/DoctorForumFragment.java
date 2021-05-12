
package com.team13.doctorclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.ForumTopicAdapter;
import com.team13.doctorclient.models.ForumModel;
import com.team13.doctorclient.models.Question;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.ForumService;

import java.util.ArrayList;
import java.util.Arrays;

public class DoctorForumFragment extends Fragment {

        View view;
        int page = 1;
        int totalPage = 1;
        int results = 1;
        String searchKey = "";
        ForumTopicAdapter forumTopicAdapter;
        ProgressBar progressBar;

        public static DoctorForumFragment newInstance() {
            return new DoctorForumFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_doctor_forum, container, false);
            progressBar = view.findViewById(R.id.loading_page);
            RecyclerView questionList = view.findViewById(R.id.question_list);
            forumTopicAdapter = new ForumTopicAdapter(view.getContext());
            forumTopicAdapter.setListener(new ForumTopicAdapter.ForumItemListener() {
                @Override
                public void openDetail(ForumModel.Topic topic) {
                    ForumDetailFragment fragment = ForumDetailFragment.newInstance(topic);
                    fragment.show(getFragmentManager(), fragment.getTag());
                }

                @Override
                public void loadNextPage() {
                    if(page < totalPage) {
                        callGetApi(++page);
                    }
                }
            });
            questionList.setAdapter(forumTopicAdapter);
            questionList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            callGetApi(1);
            return view;
        }

//        private void handlePages(){
//            if (page >= totalPage)  view.findViewById(R.id.more_btn).setEnabled(false);
//            else view.findViewById(R.id.more_btn).setEnabled(true);
//        }

        private void callGetApi(int nextPage) {
            ForumService service = new ForumService();
            if (nextPage == 1) forumTopicAdapter.reset();
            progressBar.setVisibility(View.VISIBLE);
            service.get(nextPage, new OnSuccessResponse<ForumModel>() {
                @Override
                public void onSuccess(ForumModel models) {
                    forumTopicAdapter.appendData(new ArrayList<>(Arrays.asList(models.getTopics())));
                    page = nextPage;
                    totalPage = models.getTotalPage();
                    results = models.getNumOfTopics();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }