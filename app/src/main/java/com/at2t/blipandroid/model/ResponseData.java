package com.at2t.blipandroid.model;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("data")
    Data data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public class Data {
        @SerializedName("isUserExists")
        private boolean isUserExists;

        @SerializedName("access_token")
        private String access_token;

        public boolean isUserExists() {
            return isUserExists;
        }

        public String getAccessToken() {
            return access_token;
        }
    }

}

