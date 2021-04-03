package com.team13.doctorclient.models;

import java.io.Serializable;

public class Question implements Serializable {

    String questionId;
    String patientId;
    String date;
    String time;
    String content="";
    int state; // 0: unread, 1: seen, 2: answered
    public Question(String patientId, String questionId, String date, String time, String content){
        this.patientId = patientId;
        this.questionId = questionId;
        this.date = date;
        this.time = time;
        this.content = content;
        state=0;
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
}
