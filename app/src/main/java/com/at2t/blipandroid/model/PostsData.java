package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class PostsData {
    @SerializedName("postText")
    private String message;

    @SerializedName("postId")
    private int postId;

    @SerializedName("title")
    private String title;

    @SerializedName("attachmentStreamId")
    private String postAttachmentId;

    @SerializedName("auditCreatedDate")
    private long postCreatedDate;

    @SerializedName("auditCreatedBy")
    private int auditCreatedBy;

    @SerializedName("institutionName")
    private String institutionName;

    @SerializedName("relTenantInstitutionId")
    private int relTenantInstitutionId;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastName")
    private String lastName;

    public int getAuditCreatedBy() {
        return auditCreatedBy;
    }

    public void setAuditCreatedBy(int auditCreatedBy) {
        this.auditCreatedBy = auditCreatedBy;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public int getRelTenantInstitutionId() {
        return relTenantInstitutionId;
    }

    public void setRelTenantInstitutionId(int relTenantInstitutionId) {
        this.relTenantInstitutionId = relTenantInstitutionId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPostCreatedDate() {
        return postCreatedDate;
    }

    public void setPostCreatedDate(long postCreatedDate) {
        this.postCreatedDate = postCreatedDate;
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

    public String getPostAttachmentId() {
        return postAttachmentId;
    }

    public void setPostAttachmentId(String postAttachmentId) {
        this.postAttachmentId = postAttachmentId;
    }
}
