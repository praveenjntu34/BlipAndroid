package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileData {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private UserProfileDetails userProfileDetails;

    public UserProfileData(UserProfileDetails userProfileDetails) {
        this.userProfileDetails = userProfileDetails;
    }

    public UserProfileDetails getUserProfileDetails() {
        return userProfileDetails;
    }

    public void setUserProfileDetails(UserProfileDetails userProfileDetails) {
        this.userProfileDetails = userProfileDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
