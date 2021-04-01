package com.team13.patientclient.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

<<<<<<< Updated upstream:PatientClient/app/src/main/java/com/team13/patientclient/activities/fragments/AppointmentConfirmFragment.java
import com.team13.patientclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentConfirmFragment extends Fragment {
=======
import com.team13.doctorclient.adapters.QuestionAdapter;
import com.team13.doctorclient.models.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {
>>>>>>> Stashed changes:DoctorClient/app/src/main/java/com/team13/doctorclient/BlogFragment.java

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
<<<<<<< Updated upstream:PatientClient/app/src/main/java/com/team13/patientclient/activities/fragments/AppointmentConfirmFragment.java
    AppointmentConfirmListener listener;
    public AppointmentConfirmFragment() {
=======
    RecyclerView questionList;
    public BlogFragment() {
>>>>>>> Stashed changes:DoctorClient/app/src/main/java/com/team13/doctorclient/BlogFragment.java
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentConfirmFragment.
     */
    // TODO: Rename and change types and number of parameters
<<<<<<< Updated upstream:PatientClient/app/src/main/java/com/team13/patientclient/activities/fragments/AppointmentConfirmFragment.java
    public static AppointmentConfirmFragment newInstance(String param1, String param2) {
        AppointmentConfirmFragment fragment = new AppointmentConfirmFragment();
=======
    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
>>>>>>> Stashed changes:DoctorClient/app/src/main/java/com/team13/doctorclient/BlogFragment.java
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
<<<<<<< Updated upstream:PatientClient/app/src/main/java/com/team13/patientclient/activities/fragments/AppointmentConfirmFragment.java
        View view = inflater.inflate(R.layout.fragment_appointment_confirm, container, false);
        TextView selectedTime = view.findViewById(R.id.selected_time);
        TextView selectedService = view.findViewById(R.id.selected_service);
        TextView reason = view.findViewById(R.id.selected_reason);

        selectedService.setText(listener.getSelectedService());
        selectedTime.setText(listener.getSelectedTime());
        reason.setText(listener.getReason());
        view.findViewById(R.id.process_button).setOnClickListener(v->listener.handleConfirm());


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppointmentConfirmListener) {
            listener = (AppointmentConfirmListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AppointmentConfirmListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
=======
        View view=inflater.inflate(R.layout.fragment_blog, container, false);
        questionList= view.findViewById(R.id.question_list);
        QuestionAdapter questionAdapter = new QuestionAdapter(view.getContext(),getQuestion());
        questionList.setAdapter(questionAdapter);
        questionList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }


    ArrayList<Question> getQuestion(){
        ArrayList<Question> questions = new ArrayList<Question>(5);
        for(int i=0;i<7;++i){
            questions.add(new Question("P001","D001","27th Mar","1:30pm - 2:30pm","Why I am so beautiful"));
        }
        questions.get(2).setState(2);
        questions.get(4).setState(1);
        return questions;
    }

>>>>>>> Stashed changes:DoctorClient/app/src/main/java/com/team13/doctorclient/BlogFragment.java

    public interface AppointmentConfirmListener{
        String getSelectedTime();
        String getSelectedService();
        String getReason();
        void handleConfirm();
    }
}