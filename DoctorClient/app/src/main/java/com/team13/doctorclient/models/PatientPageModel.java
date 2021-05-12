package com.team13.doctorclient.models;

import com.google.gson.annotations.SerializedName;

public class PatientPageModel {
    @SerializedName("data")
    PatientModel[] data;

    @SerializedName("results")
    int results;

    @SerializedName("page")
    int curPage;

    @SerializedName("totalPage")
    int totalPage;

    public PatientModel[] getData() {
        return data;
    }

    public void setData(PatientModel[] data) {
        this.data = data;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
