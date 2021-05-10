package com.team13.patientclient.models;

import com.google.gson.annotations.SerializedName;
import com.team13.patientclient.Utils;

import java.io.Serializable;
import java.sql.Timestamp;

public class ForumModel {
    @SerializedName("data")
    Topics[] topics;

    @SerializedName("totalPage")
    int totalPage;

    @SerializedName("page")
    int curPage;

    @SerializedName("results")
    int numOfTopics;

    public ForumModel() {
    }

    public class Answers implements Serializable{
        @SerializedName("content")
        public String content;

        @SerializedName("display_name")
        public String authorName;

        @SerializedName("user_id")
        public String authorId;

        @SerializedName("user_type")
        public String authorType;

        public Answers(String content, String authorName, String authorId, String authorType) {
            this.content = content;
            this.authorName = authorName;
            this.authorId = authorId;
            this.authorType = authorType;
        }
    }

    public class Topics implements Serializable {
        @SerializedName("topic")
        String topicString;

        @SerializedName("answers")
        Answers[] answers;

        @SerializedName("created_at")
        long time;

        public String getTime () {
            return Utils.dateNumberToString(time);
        }

        public boolean hasAnswer() {
            return answers != null && answers.length > 0;
        }

        public Answers getFirstAnswer() {
            if (!hasAnswer()) return null;
            return answers[0];
        }

        public Topics(String topicString) {
            this.topicString = topicString;
        }

        public String getTopicString() {
            return topicString;
        }

        public int getAnswerCount() { return answers.length;}

        public Answers[] getAnswers() {
            return answers;
        }
    }

    public Topics[] getTopics() {
        return topics;
    }

    public void setTopics(Topics[] topics) {
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
