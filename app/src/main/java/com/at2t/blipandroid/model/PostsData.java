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
