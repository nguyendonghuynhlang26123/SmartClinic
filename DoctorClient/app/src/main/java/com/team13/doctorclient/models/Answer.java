package com.team13.doctorclient.models;

public class Answer {
    String QuestionId;
    String Answer;

    public Answer(String QuestionId,String Answer){
        this.QuestionId=QuestionId;
        this.Answer= Answer;
    }
    public String getAnswer(){
        return this.Answer;
    }
}
