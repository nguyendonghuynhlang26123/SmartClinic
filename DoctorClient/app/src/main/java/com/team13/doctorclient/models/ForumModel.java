package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;
import com.team13.doctorclient.Utils;

import java.io.Serializable;

public class ForumModel {
    @SerializedName("data")
    Topic[] topics;

    @SerializedName("totalPage")
    int totalPage;

    @SerializedName("page")
    int curPage;

    @SerializedName("results")
    int numOfTopics;

    public ForumModel() {
    }

    public static class Answer implements Serializable {
        @SerializedName("content")
        public String content;

        @SerializedName("display_name")
        public String authorName;

        @SerializedName("user_id")
        public String authorId;

        @SerializedName("user_type")
        public String authorType;

        public Answer(String content, String authorName, String authorId, String authorType) {
            this.content = content;
            this.authorName = authorName;
            this.authorId = authorId;
            this.authorType = authorType;
        }
    }

    public static class Topic implements Serializable {
        @SerializedName("_id")
        String id;

        @SerializedName("topic")
        String topicString;

        @SerializedName("answers")
        Answer[] answers;

        @SerializedName("created_at")
        long time;

        public String getId() {
            return id;
        }

        public String getTime () {
            return Utils.dateNumberToString(time);
        }

        public boolean hasAnswer() {
            return answers != null && answers.length > 0;
        }

        public Answer getFirstAnswer() {
            if (!hasAnswer()) return null;
            return answers[0];
        }

        public Topic(String topicString) {
            this.topicString = topicString;
            time = System.currentTimeMillis();
        }

        public String getTopicString() {
            return topicString;
        }

        public int getAnswerCount() { return answers.length;}

        public Answer[] getAnswers() {
            return answers;
        }
    }

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getNumOfTopics() {
        return numOfTopics;
    }

    public void setNumOfTopics(int numOfTopics) {
        this.numOfTopics = numOfTopics;
    }


}
