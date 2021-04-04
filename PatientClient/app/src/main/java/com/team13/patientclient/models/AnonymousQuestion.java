package com.team13.patientclient.models;

public class AnonymousQuestion {
    String content;
    String questionId;
    String date;
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
}
