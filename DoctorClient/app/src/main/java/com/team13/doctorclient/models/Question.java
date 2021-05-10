package com.team13.doctorclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    String questionId;
    String date;
    String time;
    String content="";
    ArrayList<String> answers;
    int state; // 0: unread, 1: seen, 2: answered
    public Question(String questionId, String date, String time, String content){
        this.questionId = questionId;
        this.date = date;
        this.time = time;
        this.content = content;
        state=0;
        answers= new ArrayList<>(2);
    }
    public void setState(int state){
        this.state=state;
    }
    public int getState(){
        return state;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void addAnswer(String answer){
        answers.add(answer);
    }
    public int getAnswerCount(){
        return answers.size();
    }
    public String[] getAnswers(){
        String[] result = new String[answers.size()];
        return answers.toArray(result);
    }
}
