package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class PostsData {
    @SerializedName("postText")
    private String message;

    @SerializedName("postId")
    private int postId;

    @SerializedName("title")
    private String title;

    @SerializedName("sectionId")
    private int sectionId;

    @SerializedName("relTenantInstitutionId")
    private int relTenantInstitutionId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(int relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }
}
