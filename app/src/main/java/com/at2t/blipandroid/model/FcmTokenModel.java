package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class FcmTokenModel {
    @SerializedName("id")
    private int id;

    @SerializedName("token")
    private String token;

    public FcmTokenModel(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
