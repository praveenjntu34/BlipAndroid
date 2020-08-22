package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileData {

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
}
