package com.team13.doctorclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.AnswerAdapter;
import com.team13.doctorclient.models.Answer;
import com.team13.doctorclient.models.Question;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {
    Question question;
    TextView questionView;
    AnswerAdapter answerAdapter;
    RecyclerView answerList;
    EditText answerText;
    ImageButton sendBtn;
    String mess;
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent i= getIntent();
        question = (Question)i.getSerializableExtra("question");
        answerText=findViewById(R.id.answer);
        sendBtn=findViewById(R.id.send);
        answerList= findViewById(R.id.answer_list);
        questionView= findViewById(R.id.answer_question);
        questionView.setText(question.getContent());
        sendBtn.setOnClickListener(v -> {
            mess= answerText.getText().toString();
            answerText.setText("");
            if(answerAdapter==null && !mess.isEmpty()){
                answerAdapter= new AnswerAdapter(this,mess);
                answerList.setAdapter(answerAdapter);
                answerList.setLayoutManager(new LinearLayoutManager(this));
                // Send state change request to server
                //*** TODO ***//
                question.setState(2);
            }
            else if(answerAdapter!= null && !mess.isEmpty()){
                answerAdapter.add_answer(mess);
            }


        });
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v-> finish());

    }


}