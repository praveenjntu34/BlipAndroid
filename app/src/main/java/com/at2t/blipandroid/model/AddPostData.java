package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class AddPostData {
    @SerializedName("message")
    private String message;

    @SerializedName("postId")
    private int postId;

    @SerializedName("title")
    private String title;

    @SerializedName("relTenantInstitutionId")
    private int relTenantInstitutionId;

    @SerializedName("sectionId")
    private int sectionId;

    @SerializedName("attachmentStreamId")
    private String postAttachmentId;

    public String getPostAttachmentId() {
        return postAttachmentId;
    }

    public AddPostData(String message, int postId, String title, int relTenantInstitutionId, int sectionId) {
        this.message = message;
        this.postId = postId;
        this.title = title;
        this.relTenantInstitutionId = relTenantInstitutionId;
        this.sectionId = sectionId;
    }

    public void setPostAttachmentId(String postAttachmentId) {
        this.postAttachmentId = postAttachmentId;
    }

    public int getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(int relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

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

}

