package com.team13.patientclient.models;

import java.io.Serializable;

public class AnonymousQuestion implements Serializable {
    String content;
    String questionId;
    String date;
    String[] answers;
    public AnonymousQuestion(String content, String questionId, String date) {
        this.content = content;
        this.questionId = questionId;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getDate() {
        return date;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public boolean hasAnswer(){
        return answers != null;
    }

    public String getFirstAnswer(){
        return answers[0];
    }

    public int getAnswerCount(){
        return answers.length;
    }

    public String[] getAnswers() {
        return answers;
    }
}
