package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentDataModel{

    @SerializedName("data")
    List<ParentLoginData> parentLoginDataList;

    public ParentDataModel(List<ParentLoginData> parentLoginDataList) {
        this.parentLoginDataList = parentLoginDataList;
    }

    public List<ParentLoginData> getParentLoginDataList() {
        return parentLoginDataList;
    }

    public void setParentLoginDataList(List<ParentLoginData> parentLoginDataList) {
        this.parentLoginDataList = parentLoginDataList;
    }
}
